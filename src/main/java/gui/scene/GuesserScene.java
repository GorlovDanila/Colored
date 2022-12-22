package gui.scene;

import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

import static gui.Main.WINDOW_H;
import static gui.Main.WINDOW_W;

public class GuesserScene {
    public static void display(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/guesser.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_W, WINDOW_H);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setMinWidth(WINDOW_W);
            stage.setMinHeight(WINDOW_H);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
