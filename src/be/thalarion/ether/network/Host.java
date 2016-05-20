package be.thalarion.ether.network;

import java.net.InetAddress;

/**
 * Host
 * @author florian
 */
public class Host {
    
    public static enum TYPE {
        LAPTOP,
        DESKTOP,
        MOBILE
    }
    
    private final String name;
    private final InetAddress[] address;
    private final int port;
    private final TYPE type;

    public Host(String name, InetAddress[] address, int port, TYPE type) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.type = type;
    }

    public String getName() { return name; }
    public TYPE getType() { return type; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Host))
            return false;
        
        Host host = (Host) obj;
        
        if (host.name == name && host.address == address && host.port == port)
            return true;
        return false;
    }
    
}
