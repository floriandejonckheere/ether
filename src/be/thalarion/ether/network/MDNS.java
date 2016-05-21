package be.thalarion.ether.network;

import be.thalarion.ether.Ether;
import be.thalarion.ether.gui.ApplicationController;
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
public class MDNS {
    
    public static final String SERVICE_TYPE = "_ether._tcp.local.";
    public static final UUID IDENTIFIER = UUID.randomUUID();
    public static final int WEIGHT = 0;
    public static final int PRIORITY = 0;
    
    public static final Map<String, byte[]> PROPERTIES = new HashMap<String, byte[]>() {{
        put("type", "laptop".getBytes(StandardCharsets.UTF_8));
        put("name", Name.generate().getBytes());
    }};
    
    private static JmDNS jmdns;
    
    public boolean isRegistered() { return (jmdns != null); }
    
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
                            MDNS.IDENTIFIER, 
                            Ether.getInstance().getServer().getAddress(),
                            Ether.getInstance().getServer().getPort()
                    ));
                    
                    jmdns = JmDNS.create(
                            InetAddress.getLocalHost().getHostName()
                    );

                    ServiceInfo serviceInfo = ServiceInfo.create(MDNS.SERVICE_TYPE,
                            MDNS.IDENTIFIER.toString(),
                            Ether.getInstance().getServer().getPort(),
                            MDNS.WEIGHT,
                            MDNS.PRIORITY,
                            MDNS.PROPERTIES
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
