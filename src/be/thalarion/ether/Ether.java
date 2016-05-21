package be.thalarion.ether;

import be.thalarion.ether.network.Host;
import be.thalarion.ether.network.MDNS;
import be.thalarion.ether.network.Server;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Application class
 * 
 * @author florian
 */
public class Ether {
    
    private static Ether instance;
    
    private final MDNS mdns;
    private final Server server;
    
    private ObservableList<Host> hosts;
    
    private Ether() {
        hosts = FXCollections.observableList(new ArrayList<>(), (Host host) -> {
            return new Observable[]{ host };
        });
        
        mdns = new MDNS();
        server = new Server();
        
        server.start();
        mdns.register();
    }
    
    public static Ether getInstance() {
        if (instance == null)
            instance = new Ether();
        
        return instance;
    }
    
    public void stop() {
        if (mdns.isRegistered())
            mdns.unregister();
        
        server.stop();
    }

    public Server getServer() { return server; }
    
    public Host getHost(String name) {
        UUID uuid = UUID.fromString(name);
        
        for (Host h: hosts)
            if (h.getUUID().equals(uuid))
                return h;
        
        Host host = new Host(uuid);
        hosts.add(host);
        return host;
    }
    public void removeHost(String name) {
        UUID uuid = UUID.fromString(name);

        Iterator<Host> it = hosts.iterator();
        while (it.hasNext())
            if (it.next().getUUID().equals(uuid))
                it.remove();
    }
    
    public ObservableList<Host> getObservableHostList() { return hosts; }
    
}
