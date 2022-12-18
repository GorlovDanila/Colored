package gui.scene;

import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthScene {
    public static void display(Stage stage, int WINDOW_W, int WINDOW_H) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_W, WINDOW_H);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
