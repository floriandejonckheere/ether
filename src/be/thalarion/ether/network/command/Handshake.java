package be.thalarion.ether.network.command;

import be.thalarion.ether.Ether;

/**
 * Protocol handshake
 * 
 * @author florian
 */
public class Handshake implements Command {

    @Override
    public void execute() {
        System.out.println("Ether " + Ether.API_VERSION);
    }
    
}
