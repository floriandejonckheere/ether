package be.thalarion.ether.gui;

import be.thalarion.ether.network.Host.TYPE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * HostEntry controller
 * @author florian
 */
public class HostEntryController {
    
    @FXML
    private ImageView type;
    @FXML
    private Label name;
    
    public void setName(String name) { this.name.setText(name); }
    public void setType(TYPE type) {
        this.type.getStyleClass().clear();
        
        switch (type) {
            case DESKTOP:
                this.type.getStyleClass().add("type-desktop");
                break;
            case LAPTOP:
                this.type.getStyleClass().add("type-laptop");
                break;
            case MOBILE:
                this.type.getStyleClass().add("type-mobile");
                break;
            default:
                this.type.getStyleClass().add("type-unknown");
        }
    }
    
}
