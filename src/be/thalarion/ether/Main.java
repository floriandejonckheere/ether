package be.thalarion.ether;

import be.thalarion.ether.gui.ApplicationController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ether. P2P file transfer
 * @author Florian Dejonckheere
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) 
            throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/Application.fxml"));

        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Ether");
        
        ApplicationController appCtrl = loader.getController();
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { launch(args);}
    
}
