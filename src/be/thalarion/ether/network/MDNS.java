package be.thalarion.ether.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 *
 * @author florian
 */
public class MDNS {
    
    public static final String SERVICE_TYPE = "_ether._tcp.local";
    public static final String NAME = "Ether";
    public static final int WEIGHT = 0;
    public static final int PRIORITY = 0;
    
    public static final Map<String, String> PROPERTIES = new HashMap<String, String>() {{
        put("type", "laptop");
    }};
    
    private static JmDNS jmdns;
    
    public boolean isRegistered() { return (jmdns != null); }
    
    public void register() {
        new Thread(() -> {
            try {
                synchronized (MDNS.class) {
                    synchronized (Server.class) {
                        while (!Server.started) {
                            Server.class.wait();
                        }
                    }
                    
                    System.out.println("Service started on port " + Server.port);
                    
                    jmdns = JmDNS.create(
                            InetAddress.getLocalHost().getHostName()
                    );
                    jmdns.addServiceListener(MDNS.SERVICE_TYPE + ".", new MDNSListener());

                    ServiceInfo serviceInfo = ServiceInfo.create(MDNS.SERVICE_TYPE,
                            MDNS.NAME,
                            Server.port,
                            MDNS.WEIGHT,
                            MDNS.PRIORITY,
                            MDNS.PROPERTIES
                    );

                    jmdns.registerService(serviceInfo);
                }
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }).start();
    }
    
    public void unregister() {
        if (jmdns != null)
            new Thread(() -> {
                synchronized (MDNS.class) {
                    jmdns.unregisterAllServices();
                    try {
                        jmdns.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        jmdns = null;
                    }
                }
            }).start();
    }

}
