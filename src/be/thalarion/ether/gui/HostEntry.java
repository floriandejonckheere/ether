package be.thalarion.ether.gui;

import be.thalarion.ether.network.Host;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

/**
 * Host list entry
 * 
 * @author florian
 */
public class HostEntry extends HBox {
    
    private final HostEntryController controller;

    private final Host host;
    
    public HostEntry(Host host) {
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
        this.host = host;
        
        controller.setName(host.getName());
        controller.setType(host.getType());
    }

    public Host getHost() { return host; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final HostEntry other = (HostEntry) obj;
        return (this.host.equals(other.host));
    }

}
