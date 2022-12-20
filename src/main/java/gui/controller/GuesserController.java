package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
    public Canvas canvas;
    @FXML
    public BorderPane bp;

    public Button newRoundBtn;
    public Label newRoundLabel;

    public void wordBtnAction(ActionEvent actionEvent) {
    }

    public void changeCanvasToBtn() {
        canvas.setVisible(false);

        GridPane gp = new GridPane();
        gp.setVgap(8);
        gp.setAlignment(Pos.CENTER);

        newRoundLabel = new Label("Новый раунд");
        GridPane.setConstraints(newRoundLabel, 0, 0);

        newRoundBtn = new Button("Начать");
        GridPane.setConstraints(newRoundBtn, 0, 1);

        gp.getChildren().addAll(newRoundLabel, newRoundBtn);

        bp.setCenter(gp);

    }
}
