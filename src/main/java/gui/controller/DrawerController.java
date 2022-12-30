package gui.controller;

import com.google.gson.Gson;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import protocols.MessagePacket;

import javax.imageio.ImageIO;
import java.io.File;
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
    public VBox vbox;
    @FXML
    public ListView<String> listView;

    File file = new File("src/main/resources/DrawImages/draw.png");

    public GraphicsContext gc;
    private final String[] players = {"chepugash", "w1nway", "gorloff228"};

    @FXML
    public void cpAction(ActionEvent actionEvent) {
        gc.setStroke(cp.getValue());
    }
    public void changeVisibility() {
        if (canvas.isVisible()) {
            canvas.setVisible(false);
            bp.getChildren().remove(canvas);
            vbox.setVisible(true);
            bp.setCenter(vbox);
            wordLabel.setVisible(false);

            cp.setVisible(false);
            slider.setVisible(false);
            sliderLabel.setVisible(false);
            wordLabel.setVisible(false);
        } else {
            vbox.setVisible(false);
            bp.getChildren().remove(vbox);
            canvas.setVisible(true);
            initCanvas();
            bp.setCenter(canvas);
            wordLabel.setVisible(true);

            cp.setVisible(true);
            slider.setVisible(true);
            sliderLabel.setVisible(true);
            wordLabel.setVisible(true);
        }
    }

    public void initCanvas() {
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineWidth(1);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
            String subtype = AuthController.client.getGameThread().readPacket();
            if (subtype.equals("SUBTYPE_END_ROUND")) {
                Gson gson = new Gson();
                String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);
                System.out.println(result + "выиграл");
                changeVisibility();
            } else {
                AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
            }
        });
    }

    @FXML
    public void initialize() {
        initCanvas();

        listView.getItems().addAll(players);

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

        bp.getChildren().remove(canvas);
    }

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) {
        AuthController.client.getGameThread().writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
        String correctWord = (String) AuthController.client.getGameThread().readObject(3);
        System.out.println(correctWord);
        wordLabel.setText(correctWord);
        changeVisibility();

        AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);

        Gson gson = new Gson();
       String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);

        if (result != null) {
            System.out.println(result + "выиграл");
        }
    }


    public File onSave() {
        try {
            WritableImage writableImage = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
