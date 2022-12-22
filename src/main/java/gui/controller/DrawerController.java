package gui.controller;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    public GridPane gp;

    public GraphicsContext gc;

    public static boolean gameIsActive = true;

    @FXML
    public void cpAction(ActionEvent actionEvent) {
        gc.setStroke(cp.getValue());
    }

//    final Task task = new Task() {
//        @Override
//        protected Object call() throws Exception {
//            for (int i = 0; i < 100; i++) {
//                AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
//                Thread.sleep(5000);
//            }
//            return 1;
//        }
//    };


    public void changeVisibility() {
        if (canvas.isVisible()) {
            canvas.setVisible(false);
            bp.getChildren().remove(canvas);
            gp.setVisible(true);
            bp.getChildren().add(gp);
            bp.setCenter(gp);

            wordLabel.setVisible(false);
        } else {
            gp.setVisible(false);
            bp.getChildren().remove(gp);
            canvas.setVisible(true);
            bp.getChildren().add(canvas);
            bp.setCenter(canvas);

            wordLabel.setVisible(true);
        }
    }


    @FXML
    public void initialize() throws IOException, InterruptedException {

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

//        task.setOnSucceeded((EventHandler<WorkerStateEvent>) event -> {
//            int result = (int) task.getValue(); // result of computation
//            // update UI with result
//
//
//        });
//
//        Thread t = new Thread(task);
//        t.setDaemon(true); // thread will not prevent application shutdown
//        t.start();
//        Thread.sleep(10000);
//        new Thread(() -> {
//            while (gameIsActive) {
//                AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
        //AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
//            Thread.sleep(5000);
//        }
//        }).start();
//        while (gameIsActive) {
        Gson gson = new Gson();
//        Thread.sleep(5000);
//        while (!WaitingCreatorController.nextSceneFlag) {
//            System.out.println(1);
//            Thread.sleep(1000);
//        }
//        if(AuthController.client.getGameThread().readPacket().equals("SUBTYPE_CORRECT_WORD")) {
//            String correctWord = (String) AuthController.client.getGameThread().readObject(3);
//            System.out.println(correctWord);
//            wordLabel.setText(correctWord);
            AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
//        }
////            AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
////            Thread.sleep(5000);
//        }
//        for (int i = 0; i < 100; i++) {
//            AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
//            Thread.sleep(5000);
//        }
         gp.getChildren().remove(canvas);
}

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) {
        changeVisibility();

    }
     public File onSave() {
        try {
            WritableImage writableImage = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", new File("src/main/resources/DrawImages/drawable.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File("src/main/resources/DrawImages/drawable.png");
        }
}
