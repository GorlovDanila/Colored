package gui.controller;

import com.google.gson.Gson;
import core.Room;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.layout.VBox;
import protocols.MessagePacket;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;

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
    public VBox vbox;
    @FXML
    public Label wordLabel;
    @FXML
    public ListView<String> listView;

    private final String[] players = {"chepugash", "w1nway", "gorloff228"};

    File file;

    Thread thread;

    public Button newRoundBtn;
    public Label newRoundLabel;

    //отсюда берём слово
    public void wordBtnAction(ActionEvent actionEvent) {
        Gson gson = new Gson();
        AuthController.client.getGameThread().writeObject(wordInput.getText(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_JSON, 1);
        String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);
        System.out.println(result);
        thread.interrupt();
        if(result.equals("Вы угадали")) {
            System.out.println(AuthController.client.getName() + " выиграл");
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
            vbox.setVisible(true);
            bp.setCenter(vbox);

            wordLabel.setVisible(false);
            wordBtn.setVisible(false);
            wordInput.setVisible(false);
        } else {
            vbox.setVisible(false);
            bp.getChildren().remove(vbox);
            iv.setVisible(true);
            bp.setCenter(iv);

            wordLabel.setVisible(true);
            wordBtn.setVisible(true);
            wordInput.setVisible(true);
        }
    }

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) throws InterruptedException, IOException {
//        changeVisibility();
        AuthController.client.getGameThread().writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
        changeVisibility();
        file = (File) AuthController.client.getGameThread().readObject(2);

        iv.setImage(SwingFXUtils.toFXImage(ImageIO.read(file), null));



        thread = new Thread(() -> {
            Runnable updater = () -> {
                file = (File) AuthController.client.getGameThread().readObject(2);
                try {
                    Image image = SwingFXUtils.toFXImage(ImageIO.read(file), null);
                    iv.setImage(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                Platform.runLater(updater);
            }
        });
        thread.setDaemon(true);
        thread.start();

//        iv.setOnMouseMoved(mouseEvent -> {
//            file = (File) AuthController.client.getGameThread().readObject(2);
//            try {
//                Image image = SwingFXUtils.toFXImage(ImageIO.read(file), null);
//                iv.setImage(image);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });


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
