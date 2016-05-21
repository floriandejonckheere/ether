package be.thalarion.ether.network;

import be.thalarion.ether.Ether;
import be.thalarion.ether.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Client control layer
 * @author florian
 */
public class Client implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    
    public Client(Socket socket) 
            throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    @Override
    public void run() {
        try {
            String input;
            
            // Protocol handshake
            out.write(String.format("OK ETHER %d\n", Ether.API_VERSION));

            // Receive protocol commands
            input = in.readLine();
            switch (input) {
                case "LIST SHARES":
                    System.out.println("Listing shares");
                    break;
                default:
                    out.write("ERR\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {}
        }
    }
    
}
