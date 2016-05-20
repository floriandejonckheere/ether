package be.thalarion.ether;

import be.thalarion.ether.network.MDNS;
import be.thalarion.ether.network.Server;

/**
 *
 * @author florian
 */
public class Ether {
    
    private final MDNS mdns;
    private final Server server;
    
    public Ether() {
        mdns = new MDNS();
        server = new Server();
        
        server.start();
        mdns.register();
    }
    
    public void stop() {
        if (mdns.isRegistered())
            mdns.unregister();
        
//        server.stop();
    }
    
}
