package be.thalarion.ether;

import be.thalarion.ether.files.PrefixTree;
import be.thalarion.ether.network.mdns.mDNSHost;
import be.thalarion.ether.network.mdns.mDNS;
import be.thalarion.ether.network.server.Server;
import java.io.File;
import java.util.ArrayList;
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
    
    public static final int API_VERSION = 1;
    
    private static Ether instance;
    
    private final mDNS mdns;
    private final Server server;
    
    private final ObservableList<mDNSHost> hosts;
    private final PrefixTree tree;
    
    private Ether() {
        hosts = FXCollections.observableList(new ArrayList<>(), (mDNSHost host) -> {
            return new Observable[]{ host };
        });
        
        tree = new PrefixTree();
        
        mdns = new mDNS();
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
    
    public mDNSHost getHost(String name) {
        UUID uuid = UUID.fromString(name);
        
        for (mDNSHost h: hosts)
            if (h.getUUID().equals(uuid))
                return h;
        
        mDNSHost host = new mDNSHost(uuid);
        hosts.add(host);
        return host;
    }
    
    public void removeHost(String name) {
        UUID uuid = UUID.fromString(name);

        for (mDNSHost host: hosts)
            if (host.getUUID().equals(uuid))
                host.setActive(false);
    }
    
    public ObservableList<mDNSHost> getObservableHostList() { return hosts; }
    public UUID getUUID() { return mdns.getUUID(); }
    public Server getServer() { return server; }
    
    public mDNSHost getLocalhost() { return mdns.getLocalhost(); }
    
    public void addFile(File file) { tree.add(file); tree.printTree(); }
    public PrefixTree getFiles() { return tree; }
    
}
