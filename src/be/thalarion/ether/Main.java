package be.thalarion.ether;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ether. P2P file transfer
 * @author florian
 */
public class Main extends Application {
    
    public static final int VERSION = 1;
    
    private static Ether ether;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) 
            throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/Application.fxml"));

        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Ether");
        
        ether = Ether.getInstance();
        
        stage.show();
        
        stage.setOnCloseRequest((ev) -> {
            ev.consume();
            ether.stop();
        });
    }
}
