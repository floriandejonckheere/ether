package be.thalarion.ether.gui;

import be.thalarion.ether.network.Host.TYPE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * HostEntry controller
 * 
 * @author florian
 */
public class HostEntryController {
    
    @FXML
    private ImageView type;
    @FXML
    private Label name;
    
    public void setName(String name) {
        if (name == null || name.isEmpty()) return;
        
        this.name.setText(name);
    }
    
    public void setType(TYPE type) {
        if (type == null) return;
        
        this.type.getStyleClass().clear();
        
        switch (type) {
            case LINUX:
                this.type.getStyleClass().add("type-linux");
                break;
            case ANDROID:
                this.type.getStyleClass().add("type-android");
                break;
            case WINDOWS:
                this.type.getStyleClass().add("type-windows");
                break;
            case APPLE:
                this.type.getStyleClass().add("type-apple");
            default:
                this.type.getStyleClass().add("type-unknown");
        }
    }
    
}
