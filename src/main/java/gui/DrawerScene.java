package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DrawerScene {
    public static void display(Stage stage, int lobbyId, String nickname, int WINDOW_W, int WINDOW_H) {
        Canvas canvas = new Canvas(720, 480);

        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        ColorPicker cp = new ColorPicker();
        cp.setValue(Color.BLACK);
        cp.setOnAction(e -> {
            gc.setStroke(cp.getValue());
        });

        Slider slider = new Slider();
        Label label = new Label("1.0");
        slider.setMin(1);
        slider.setMax(100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener(e -> {
            double value = slider.getValue();
            String str = String.format("%.1f", value);
            label.setText(str);
            gc.setLineWidth(value);
        });

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, WINDOW_W, WINDOW_H);

        scene.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });

        // Top Menu
        GridPane topGrid = new GridPane();
        topGrid.addRow(0, cp, slider, label);
        topGrid.setHgap(20);
        topGrid.setAlignment(Pos.TOP_CENTER);
        topGrid.setPadding(new Insets(20, 0, 0, 0));

        scene.setOnMouseDragged(e -> {
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });

        // Bottom Inf
        GridPane bottomGrid = setBottomLayout(lobbyId, nickname);

        // Left Inf
        Label word = new Label("Слово");
        Label timer = new Label("00:00");

        GridPane leftGrid = new GridPane();
        leftGrid.addColumn(0, timer, word);
        leftGrid.setVgap(20);
        leftGrid.setAlignment(Pos.CENTER);

        // Main Layout
        borderPane.setTop(topGrid);
        borderPane.setCenter(canvas);
        borderPane.setBottom(bottomGrid);
        borderPane.setLeft(leftGrid);

        stage.setScene(scene);
        stage.show();
    }

    public static GridPane setBottomLayout(int lobbyId, String nickname) {
        Label currentLobby = new Label("" + lobbyId);
        Label currentNickname = new Label("" + nickname);

        GridPane bottomGrid = new GridPane();
        bottomGrid.addRow(0, currentLobby, currentNickname);
        bottomGrid.setAlignment(Pos.TOP_CENTER);
        bottomGrid.setHgap(100);
        bottomGrid.setPadding(new Insets(20, 20, 20, 20));

        return bottomGrid;
    }
}
