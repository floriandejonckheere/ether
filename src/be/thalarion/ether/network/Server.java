package be.thalarion.ether.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server control layer
 * @author florian
 */
public class Server {
    
    private Thread server;
    
    public void start() {
        server = new Thread(new ServerThread());

        server.start();
    }
    
    public void stop() {
        server.interrupt();
    }
    
    private class ServerThread implements Runnable {

        private ServerSocket server;
        private final List<Socket> clients;

        public ServerThread() {
            clients = new ArrayList<>();
        }

        @Override
        public void run() {
            try {
                server = new ServerSocket(MDNS.PORT);

                while (true) {
                    Socket client = server.accept();
                    clients.add(client);
                    new Thread(new Client(client)).start();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                for (Socket s: clients)
                    if (!s.isClosed())
                        try { s.close(); } catch (IOException ex) {}
            }
        }

}
}
