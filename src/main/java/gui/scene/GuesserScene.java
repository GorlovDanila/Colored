package gui.scene;

import gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuesserScene {
    public static void display(Stage stage, int lobbyId, String nickname, int WINDOW_W, int WINDOW_H) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/guesser.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_W, WINDOW_H);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
