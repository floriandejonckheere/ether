package be.thalarion.ether.network;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * Host
 * 
 * @author florian
 */
public class Host implements Observable {
    
    public static enum TYPE {
        LAPTOP,
        DESKTOP,
        MOBILE
    }
    
    private final UUID uuid;
    private String name;
    private InetAddress[] address;
    private int port;
    private TYPE type;

    private final List<InvalidationListener> listeners;
    
    public Host(UUID uuid) {
        this.uuid = uuid;
        listeners = new ArrayList<>();
    };

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        for (InvalidationListener il: listeners)
            il.invalidated(this);
    }

    public InetAddress[] getAddress() { return address; }
    public void setAddress(InetAddress[] address) {
        this.address = address;
        for (InvalidationListener il: listeners)
            il.invalidated(this);
    }

    public int getPort() { return port; }
    public void setPort(int port) {
        this.port = port;
        for (InvalidationListener il: listeners)
            il.invalidated(this);
    }
    
    public TYPE getType() { return type; }
    public void setType(TYPE type) {
        this.type = type;
        for (InvalidationListener il: listeners)
            il.invalidated(this);
    }

    public UUID getUUID() { return uuid; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Host other = (Host) obj;
        return this.uuid.equals(other.uuid);
    }
    
    @Override
    public void addListener(InvalidationListener listener) { listeners.add(listener); }

    @Override
    public void removeListener(InvalidationListener listener) { listeners.remove(listener); }
    
}
