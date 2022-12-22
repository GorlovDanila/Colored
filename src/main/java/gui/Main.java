package gui;

import gui.scene.AuthScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static int WINDOW_H = 600;
    public static int WINDOW_W = 1080;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        try {
            stage.setTitle("Colored!");
            setUserAgentStylesheet(STYLESHEET_MODENA);
            AuthScene.display(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
