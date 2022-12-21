package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    //отсюда берём слово
    public void wordBtnAction(ActionEvent actionEvent) {

    }

    //берём файл с сервера, сетим картинку по пути, берём ответ игрока, отправляем на сервер(комнату), там проверяем на правильность,
    // если правильно, прекращаем принимать ответы, отправляем всем игрокам в поток результат, завершаем раунд, начинаем новый
    // если не правильно, продолжаем принимать ответы


}
