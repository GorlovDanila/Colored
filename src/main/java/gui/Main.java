package gui;

import gui.scene.AuthScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public int WINDOW_H = 600;
    public int WINDOW_W = 1080;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        try {
            stage.setTitle("Colored!");
            setUserAgentStylesheet(STYLESHEET_MODENA);
            AuthScene.display(stage, WINDOW_W, WINDOW_H);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
