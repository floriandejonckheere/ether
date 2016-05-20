package be.thalarion.ether.network;

import java.net.InetAddress;

/**
 * Host
 * @author florian
 */
public class Host {
    
    private final String name;
    private final InetAddress[] address;
    private final int port;

    public Host(String name, InetAddress[] address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() { return name; }

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
