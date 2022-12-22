package gui.controller;

import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import protocols.MessagePacket;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


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

    private int count = 0;

    @FXML
    public void cpAction(ActionEvent actionEvent) {
        gc.setStroke(cp.getValue());
    }
//    class ThreadedTask implements Runnable {
//
//        private String message;
//
//        public ThreadedTask(){
////            this.message = message;
//        }
//
//        @Override
//        public void run() {
//
//            AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
//
//            //scroll to the bottom of the log
////            outputLog.setScrollTop(Double.MIN_VALUE);
//        }
//    }
//    };

//    Task task = new Task<Void>() {
//        @Override
//        protected Void call() throws Exception {
//           Gson gson = new Gson();
//        String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);
//        System.out.println(result + "выиграл");
//        changeVisibility();
//            return null;
//        }
//    };
//    Timer timer = new Timer();
//    AnimationTimer timer = new AnimationTimer() {
//        @Override
//        public void  (long) {
//            //Set executable code here
//        }
//    };
//timer.start();

    public void changeVisibility() {
        if (canvas.isVisible()) {
            canvas.setVisible(false);
            bp.getChildren().remove(canvas);
            gp.setVisible(true);
            bp.setCenter(gp);

            wordLabel.setVisible(false);
        } else {
            gp.setVisible(false);
            bp.getChildren().remove(gp);
            canvas.setVisible(true);
            initCanvas();
            bp.getChildren().add(canvas);
            bp.setCenter(canvas);

            wordLabel.setVisible(true);
        }
    }

    public void initCanvas() {
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });
    }

    @FXML
    public void initialize() throws IOException, InterruptedException {
        initCanvas();

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
            AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
//            Gson gson = new Gson();
//                String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);
//                System.out.println(result + "выиграл");
        });

        bp.getChildren().remove(canvas);

//        canvas.setOnMouseClicked(mouseEvent ->  {
//                Gson gson = new Gson();
//                String result = gson.fromJson((String) AuthController.client.getGameThread().readObject(2), String.class);
//                System.out.println(result + "выиграл");
////                try {
////                    Thread.sleep(5000);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
//                changeVisibility();
//        task.setOnSucceeded((EventHandler<WorkerStateEvent>) event -> {
//            int result = (int) task.getValue(); // result of computation
//            // update UI with result
//
//

//        });
    }

    @FXML
    public void roundBtnAction(ActionEvent actionEvent) throws InterruptedException {
        AuthController.client.getGameThread().writeMessage(MessagePacket.TYPE_META, MessagePacket.SUBTYPE_START_ROUND);
//        System.out.println(AuthController.client.getGameThread().readPacket());
        String correctWord = (String) AuthController.client.getGameThread().readObject(3);
        System.out.println(correctWord);
        wordLabel.setText(correctWord);
        changeVisibility();

        AuthController.client.getGameThread().writeObject(onSave(), MessagePacket.TYPE_BOARD, MessagePacket.SUBTYPE_DEFAULT, 5);
    }



    public File onSave() {
        try {
            count++;
            WritableImage writableImage = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", new File("src/main/resources/DrawImages/drawable" + count + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File("src/main/resources/DrawImages/drawable" + count + ".png");
    }
}
