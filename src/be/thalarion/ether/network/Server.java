package be.thalarion.ether.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server control layer
 * 
 * @author florian
 */
public class Server {
    
    public static boolean started = false;
    
    private InetAddress address;
    private int port;
    
    private ServerSocket socket;
    private Thread server;
    
    public void start() {
        server = new Thread(new ServerThread());
        server.start();
    }
    
    public void stop() {
        if (socket != null)
            try {
                socket.close();
            } catch (IOException ex) {
            } finally {
                socket = null;
            }
    }
    
    public InetAddress getAddress() { return address; }
    public int getPort() { return port; }
    
    private class ServerThread implements Runnable {

        private final List<Socket> clients;

        public ServerThread() { clients = new ArrayList<>(); }
        
        @Override
        public void run() {
            try {
                socket = new ServerSocket(0);
                
                address = socket.getInetAddress();
                port = socket.getLocalPort();
                
                synchronized (Server.class) {
                    started = true;
                    Server.class.notify();
                }
                
                while (true) {
                    Socket client = socket.accept();
                    clients.add(client);
                    new Thread(new Client(client)).start();
                }
            } catch (IOException ex) {
            } finally {
                for (Socket s: clients)
                    if (!s.isClosed())
                        try { s.close(); } catch (IOException ex) {}
            }
        }
    }
    
}
