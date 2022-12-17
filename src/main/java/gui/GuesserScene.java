package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GuesserScene {
    public static void display(Stage stage, int lobbyId, String nickname, int WINDOW_W, int WINDOW_H) {
        Canvas canvas = new Canvas(720, 480);
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, WINDOW_W, WINDOW_H);

        // Bottom Inf
        GridPane bottomGrid = DrawerScene.setBottomLayout(lobbyId, nickname);

        // Right Chat
        Label guess = new Label("Угадайте загаданное слово:");
        TextField guessTF = new TextField();
        Button guessBtn = new Button("Ок");

        GridPane rightGrid = new GridPane();
        rightGrid.addColumn(0, guess, guessTF, guessBtn);
        rightGrid.setVgap(10);
        rightGrid.setAlignment(Pos.CENTER);

        // Left Inf
        Label timer = new Label("00:00");

        GridPane leftGrid = new GridPane();
        leftGrid.addColumn(0, timer);
        leftGrid.setAlignment(Pos.CENTER);

        // Main Layout
        borderPane.setCenter(canvas);
        borderPane.setBottom(bottomGrid);
        borderPane.setLeft(leftGrid);
        borderPane.setRight(rightGrid);

        stage.setScene(scene);
        stage.show();
    }


}
