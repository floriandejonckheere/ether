package be.thalarion.ether;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

/**
 * Ether. P2P file transfer
 * @author florian
 */
public class Main extends Application {
        
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

        scene.setOnDragOver((ev) -> {
            Dragboard db = ev.getDragboard();
            if (db.hasFiles()) {
                ev.acceptTransferModes(TransferMode.ANY);
            } else ev.consume();
        });
        
        scene.setOnDragDropped((ev) -> {
            Dragboard db = ev.getDragboard();
            if (db.hasFiles())
                for (File file: db.getFiles())
                    Ether.getInstance().addFile(file);

                
            ev.setDropCompleted(true);
            ev.consume();
        });
        
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
