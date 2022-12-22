package gui.controller;

import com.google.gson.Gson;
import core.Room;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import protocols.MessagePacket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    @FXML
    public Label wordLabel;
    @FXML
    public ListView<String> listView;

    private final String[] players = {"chepugash", "w1nway", "gorloff228"};

    public Button newRoundBtn;
    public Label newRoundLabel;

    public int count = 0;
    //отсюда берём слово
    public void wordBtnAction(ActionEvent actionEvent) {
        Gson gson = new Gson();
        AuthController.client.getGameThread().writeObject(wordInput.getText(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_JSON, 1);
        String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);
        System.out.println(result);
        if(result.equals("Вы угадали")) {
            //System.out.println(AuthController.client.getName() + " выиграл");
            Room.logic.setRoundActive(false);
            changeVisibility();
        }
    }


    //берём файл с сервера, сетим картинку по пути, берём ответ игрока, отправляем на сервер(комнату), там проверяем на правильность,
    // если правильно, прекращаем принимать ответы, отправляем всем игрокам в поток результат, завершаем раунд, начинаем новый
    // если не правильно, продолжаем принимать ответы

    public void changeVisibility() {
        if (iv.isVisible()) {
            iv.setVisible(false);
            bp.getChildren().remove(iv);
            gp.setVisible(true);
            bp.setCenter(gp);

            wordLabel.setVisible(false);
            wordBtn.setVisible(false);
            wordInput.setVisible(false);
        } else {
            gp.setVisible(false);
            bp.getChildren().remove(gp);
            iv.setVisible(true);
            bp.setCenter(iv);

            wordLabel.setVisible(true);
            wordBtn.setVisible(true);
            wordInput.setVisible(true);
        }
    }

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) throws InterruptedException {
//        changeVisibility();
        AuthController.client.getGameThread().writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
        changeVisibility();
        String p = (String) AuthController.client.getGameThread().readObject(2);
        System.out.println(p);
        count++;
        iv.setImage(new Image(p));

        iv.setOnMouseMoved(mouseEvent -> {
//            Image image = null;
//            count++;
            String path1 = (String) AuthController.client.getGameThread().readObject(2);
            String path = "/DrawImages/drawable2.png";
            System.out.println(path1);
//            try {
//                image = new Image(new FileInputStream(path));
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
            iv.setImage(new Image(path1));
        });


//        System.out.println(count);
//        while (true) {
           // iv.setImage(new Image((String) AuthController.client.getGameThread().readObject(2)));
//        }
//        System.out.println(2);
//            Thread.sleep(5000);
//            iv.setImage(new Image((String) AuthController.client.getGameThread().readObject(2)));
//        System.out.println(3);
           // iv.setImage(new Image((String) AuthController.client.getGameThread().readObject(2)));
//        }
//        changeVisibility();
    }

    @FXML
    public void initialize() throws IOException, InterruptedException {
        listView.getItems().addAll(players);
        iv.setImage(new Image("/test.png"));
        bp.getChildren().remove(iv);
    }
}
