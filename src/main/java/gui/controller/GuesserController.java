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

    public Button newRoundBtn;
    public Label newRoundLabel;

    public void wordBtnAction(ActionEvent actionEvent) {
    }

    public void changeCanvasToBtn() {
        iv.setVisible(false);
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

    @FXML
    public void initialize() throws IOException, InterruptedException {

            iv.setImage(new Image("/test.png"));
            Thread.sleep(5000);
            iv.setImage(new Image("/tset.png"));


    }
}
