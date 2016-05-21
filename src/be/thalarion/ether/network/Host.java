package be.thalarion.ether.network;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Host
 * 
 * @author florian
 */
public class Host implements Observable {
    
    public static enum TYPE {
        LINUX,
        WINDOWS,
        APPLE,
        ANDROID,
        UNKNOWN
    }
    
    private final UUID uuid;
    private String name;
    private InetAddress[] address;
    private int port;
    private TYPE type;
    private final BooleanProperty active;

    private final List<InvalidationListener> listeners;
    
    public Host(UUID uuid, String name, InetAddress[] address, int port, TYPE type) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
        this.port = port;
        this.type = type;
        listeners = new ArrayList<>();
        active = new SimpleBooleanProperty(true);
    }
    public Host(UUID uuid) { this(uuid, null, null, 0, null); };

    public UUID getUUID() { return uuid; }
    
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        invalidate();
    }

    public InetAddress[] getAddress() { return address; }
    public void setAddress(InetAddress[] address) {
        this.address = address;
        invalidate();
    }

    public int getPort() { return port; }
    public void setPort(int port) {
        this.port = port;
        invalidate();
    }
    
    public TYPE getType() { return type; }
    public void setType(TYPE type) {
        this.type = type;
        invalidate();
    }

    public BooleanProperty activeProperty() { return active; }
    public void setActive(boolean active) {
        this.active.set(active);
        invalidate();
    }

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
    
    private void invalidate() {
        for (InvalidationListener il: listeners)
            il.invalidated(this);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s:%d)",
                uuid,
                name,
                address,
                port);
    }
}
