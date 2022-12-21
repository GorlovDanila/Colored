package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GuesserController {
    @FXML
    public TextField wordInput;
    @FXML
    public Button wordBtn;
    @FXML
    public Label drawerLabel;
    @FXML
    public Label timerLabel;
    @FXML
    public Label lobbyIdLabel;
    @FXML
    public ImageView iv;
    @FXML
    public BorderPane bp;
    @FXML
    public GridPane gp;

    public Button newRoundBtn;
    public Label newRoundLabel;

    public void wordBtnAction(ActionEvent actionEvent) {
    }

    public void changeVisibility() {
        if (iv.isVisible()) {
            iv.setVisible(false);
            bp.getChildren().remove(iv);
            gp.setVisible(true);
            bp.getChildren().add(gp);
            bp.setCenter(gp);
        } else {
            gp.setVisible(false);
            bp.getChildren().remove(gp);
            iv.setVisible(true);
            bp.getChildren().add(iv);
            bp.setCenter(iv);
        }
    }

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) {
        changeVisibility();
    }

    @FXML
    public void initialize() throws IOException, InterruptedException {
        iv.setImage(new Image("/test.png"));
        gp.getChildren().remove(iv);
    }
}
