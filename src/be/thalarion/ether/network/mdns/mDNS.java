package be.thalarion.ether.network.mdns;

import be.thalarion.ether.Ether;
import be.thalarion.ether.gui.ApplicationController;
import be.thalarion.ether.network.mdns.mDNSHost.TYPE;
import be.thalarion.ether.network.Name;
import be.thalarion.ether.network.server.Server;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javafx.application.Platform;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * MDNS Operations
 * 
 * @author florian
 */
public class mDNS {
    
    private static final String SERVICE_TYPE = "_ether._tcp.local.";
    private static final int WEIGHT = 0;
    private static final int PRIORITY = 0;
    
    private final UUID uuid;
    private final mDNSHost localhost;
    
    private final Map<String, byte[]> properties;
    
    protected static JmDNS jmdns;
    
    public mDNS() {
        uuid = UUID.randomUUID();
        String name = Name.generate();
        
        properties = new HashMap<String, byte[]>() {{
            put("name", name.getBytes(StandardCharsets.UTF_8));
        }};
        
        String os = System.getProperty("os.name").toLowerCase();
        TYPE type = TYPE.UNKNOWN;
        if (os.contains("linux"))
            type = TYPE.LINUX;
        else if (os.contains("windows"))
            type = TYPE.WINDOWS;
        else if (os.contains("mac"))
            type = TYPE.APPLE;
        properties.put("type", type.name().getBytes(StandardCharsets.UTF_8));
        
        localhost = new mDNSHost(uuid);
        localhost.setName(String.format("%s (this computer)", name));
        localhost.setType(type);
    }
    
    public boolean isRegistered() { return (jmdns != null); }
    public UUID getUUID() { return uuid; }
    public mDNSHost getLocalhost() { return localhost; }
    
    public void register() {
        new Thread(() -> {
            Platform.runLater(() -> {
                ApplicationController.getInstance().setText("Registering on the network");
                ApplicationController.getInstance().setLoading(true);
            });
            try {
                synchronized (mDNS.class) {
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

                    ServiceInfo serviceInfo = ServiceInfo.create(mDNS.SERVICE_TYPE,
                            uuid.toString(),
                            Ether.getInstance().getServer().getPort(),
                            mDNS.WEIGHT,
                            mDNS.PRIORITY,
                            properties
                    );

                    jmdns.registerService(serviceInfo);
                    
                    jmdns.addServiceListener(mDNS.SERVICE_TYPE, new mDNSListener());
                    
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
                synchronized (mDNS.class) {
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
