package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class DrawerController {
    @FXML
    public Canvas canvas;
    @FXML
    public ColorPicker cp;
    @FXML
    public Slider slider;
    @FXML
    public Label sliderLabel;
    @FXML
    public Label wordLabel;
    @FXML
    public Label drawerLabel;
    @FXML
    public Label timerLabel;
    @FXML
    public Label lobbyIdLabel;
    @FXML
    public BorderPane bp;
    @FXML
    public Button newRoundBtn;
    @FXML
    public Label newRoundLabel;
    @FXML
    public GridPane gp;

    public GraphicsContext gc;

    @FXML
    public void cpAction(ActionEvent actionEvent) {
        gc.setStroke(cp.getValue());
    }


    public void changeVisibility() {
        if (canvas.isVisible()) {
            canvas.setVisible(false);
            bp.getChildren().remove(canvas);
            gp.setVisible(true);
            bp.getChildren().add(gp);
            bp.setCenter(gp);
        } else {
            gp.setVisible(false);
            bp.getChildren().remove(gp);
            canvas.setVisible(true);
            bp.getChildren().add(canvas);
            bp.setCenter(canvas);
        }
    }


    @FXML
    public void initialize() throws IOException {

        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        cp.setValue(Color.BLACK);
        cp.setOnAction(e -> {
            gc.setStroke(cp.getValue());
        });

        slider.valueProperty().addListener(e -> {
            double value = slider.getValue();
            String str = String.format("%.1f", value);
            sliderLabel.setText(str);
            gc.setLineWidth(value);
        });

        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });

        gp.getChildren().remove(canvas);

    }

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) {
        changeVisibility();
    }
}
