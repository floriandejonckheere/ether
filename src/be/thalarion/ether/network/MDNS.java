package be.thalarion.ether.network;

import be.thalarion.ether.Ether;
import be.thalarion.ether.gui.ApplicationController;
import be.thalarion.ether.network.Host.TYPE;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javafx.application.Platform;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * MDNS Operations
 * 
 * @author florian
 */
public class MDNS {
    
    private static final String SERVICE_TYPE = "_ether._tcp.local.";
    private static final int WEIGHT = 0;
    private static final int PRIORITY = 0;
    
    private final UUID uuid;
    
    private final Map<String, byte[]> properties;
    
    private static JmDNS jmdns;
    
    public MDNS() {
        uuid = UUID.randomUUID();
        properties = new HashMap<String, byte[]>() {{
            put("name", Name.generate().getBytes(StandardCharsets.UTF_8));
        }};
        
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux"))
            properties.put("type", TYPE.LINUX.name().getBytes(StandardCharsets.UTF_8));
        else if (os.contains("windows"))
            properties.put("type", TYPE.WINDOWS.name().getBytes(StandardCharsets.UTF_8));
        else if (os.contains("mac"))
            properties.put("type", TYPE.APPLE.name().getBytes(StandardCharsets.UTF_8));
        else
            properties.put("type", TYPE.UNKNOWN.name().getBytes(StandardCharsets.UTF_8));
    }
    
    public boolean isRegistered() { return (jmdns != null); }
    public UUID getUUID() { return uuid; }
    
    
    public void register() {
        new Thread(() -> {
            Platform.runLater(() -> {
                ApplicationController.getInstance().setText("Registering on the network");
                ApplicationController.getInstance().setLoading(true);
            });
            try {
                synchronized (MDNS.class) {
                    synchronized (Server.class) {
                        while (!Server.started)
                            Server.class.wait();
                    }
                    
                    System.out.println(String.format("[%s] Listening on %s:%d", 
                            uuid, 
                            Ether.getInstance().getServer().getAddress(),
                            Ether.getInstance().getServer().getPort()
                    ));
                    
                    jmdns = JmDNS.create(
                            InetAddress.getLocalHost(),
                            uuid.toString()
                    );

                    ServiceInfo serviceInfo = ServiceInfo.create(MDNS.SERVICE_TYPE,
                            uuid.toString(),
                            Ether.getInstance().getServer().getPort(),
                            MDNS.WEIGHT,
                            MDNS.PRIORITY,
                            properties
                    );

                    jmdns.registerService(serviceInfo);
                    
                    jmdns.addServiceListener(MDNS.SERVICE_TYPE, new MDNSListener());
                    
                    Platform.runLater(() -> {
                        ApplicationController.getInstance().setLoading(false);
                    });
                }
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }).start();
    }
    
    public void unregister() {
        if (jmdns != null)
            new Thread(() -> {
                Platform.runLater(() -> {
                    ApplicationController.getInstance().setText("Unregistering from the network");
                    ApplicationController.getInstance().setLoading(true);
                });
                synchronized (MDNS.class) {
                    jmdns.unregisterAllServices();
                    try {
                        jmdns.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        jmdns = null;
                        Platform.runLater(() -> {
                            ApplicationController.getInstance().setLoading(false);
                            Platform.exit();
                        });
                    }
                }
            }).start();
    }

}
