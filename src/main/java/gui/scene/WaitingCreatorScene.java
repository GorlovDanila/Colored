package gui.scene;

import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

import static gui.Main.WINDOW_H;
import static gui.Main.WINDOW_W;

public class WaitingCreatorScene {
    public static void display(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/waiting_creator.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_W, WINDOW_H);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
