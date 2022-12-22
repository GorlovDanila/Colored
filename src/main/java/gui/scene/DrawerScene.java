package gui.scene;

import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static gui.Main.WINDOW_H;
import static gui.Main.WINDOW_W;

public class DrawerScene {
    public static void display(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/drawer.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_W, WINDOW_H);
            stage.setMinWidth(WINDOW_W);
            stage.setMinHeight(WINDOW_H);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
