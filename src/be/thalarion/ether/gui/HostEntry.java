package be.thalarion.ether.gui;

import be.thalarion.ether.network.Host;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

/**
 * Host list entry
 * @author florian
 */
public class HostEntry extends HBox {
    
    private final HostEntryController controller;

    private Host host;
    
    public HostEntry(String name, EventHandler<ActionEvent> handler) {
        URL location = getClass().getResource("HostEntry.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);
        loader.setRoot(this);
        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        controller = loader.getController();
    }
    
    public void initialize(Host host) {
        this.host = host;

        controller.setName(host.getName());
        controller.setType(host.getType());
    }
    
}
