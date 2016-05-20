package be.thalarion.ether.gui;

import be.thalarion.ether.network.Host;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author florian
 */
public class ApplicationController {
    
    private static ApplicationController instance;

    private BooleanProperty loading;
    private ObservableList<Host> hosts;

    @FXML
    private ListView hostsListView;
    @FXML
    private StackPane loadingPane;
    
    public void initialize() {
        instance = this;
        loading = new SimpleBooleanProperty(false);
        
        loadingPane.visibleProperty().bind(loading);
        loadingPane.managedProperty().bind(loading);
        
        hosts = FXCollections.observableArrayList();
        
        hostsListView.setItems(hosts);
    }
    
    public static ApplicationController getInstance() { return instance; }
    
    public void addHost(Host host) { hosts.add(host); }
    public void removeHost(Host host) { hosts.remove(host); }
}
