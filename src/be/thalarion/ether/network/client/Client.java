package be.thalarion.ether.network.client;

import be.thalarion.ether.network.mdns.mDNSHost;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client control layer
 * 
 * @author florian
 */
public class Client {
    
    private mDNSHost host;
    private Socket socket;
    
    private BufferedReader in;
    private BufferedWriter out;
    
    public Client(mDNSHost host) {
        this.host = host;
    }
    
    public void connect() 
            throws IOException {
        for (InetAddress address: host.getAddress()) {
            socket = new Socket(address, host.getPort());
            
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            break;
        }
    }
    
}
