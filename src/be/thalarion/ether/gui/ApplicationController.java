package be.thalarion.ether.gui;

import be.thalarion.ether.Ether;
import be.thalarion.ether.network.Host;
import be.thalarion.ether.network.MDNS;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private ObservableList<HostEntry> hosts;

    @FXML
    private ListView<HostEntry> hostsListView;
    @FXML
    private StackPane loadingPane;
    @FXML
    private Label loadingLabel;
    
    public void initialize() {
        instance = this;
        loading = new SimpleBooleanProperty(false);
        
        loadingPane.visibleProperty().bind(loading);
        loadingPane.managedProperty().bind(loading);
        
        hosts = FXCollections.observableArrayList();
        
        Ether.getInstance().getObservableHostList().addListener((ListChangeListener.Change<? extends Host> c) -> {
            hosts.clear();
            
            for (Host host: Ether.getInstance().getObservableHostList())
                if (host.activeProperty().get())
                    if (host.getUUID().equals(Ether.getInstance().getUUID()))
                        hosts.add(new HostEntry(Ether.getInstance().getLocalhost()));
                    else
                        hosts.add(new HostEntry(host));
        });
        
        hostsListView.setItems(hosts);
        hostsListView.setPlaceholder(new Label("No hosts on the network"));
    }
    
    public static ApplicationController getInstance() { return instance; }
    public void setLoading(boolean loading) { this.loading.set(loading); }
    public void setText(String text) { this.loadingLabel.setText(text); }
    
}
