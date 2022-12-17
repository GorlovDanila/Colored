package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AuthScene {
    static int newLobby;
    public static void display(Stage stage, int WINDOW_W, int WINDOW_H) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Name label
        Label nameLabel = new Label("Введите никнейм: ");
        GridPane.setConstraints(nameLabel, 0, 0);

        // Name Input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        // New Lobby label
        Label newLobbyLabel = new Label("Создать лобби: ");
        GridPane.setConstraints(newLobbyLabel, 0, 1);

        // New Lobby Input
        TextField newLobbyInput = new TextField();
        GridPane.setConstraints(newLobbyInput, 1, 1);

        // New Lobby Button
        Button newLobbyBtn = new Button("Ок");
        newLobbyBtn.setOnAction(e -> {
            if (isInt(newLobbyInput)) {
                newLobby = Integer.parseInt(newLobbyInput.getText());
                AlertBox.display("Успешно",
                        "Лобби " + newLobby + " успешно создано");
            }
        });
        GridPane.setConstraints(newLobbyBtn, 2, 1);

        // Join Lobby label
        Label joinLobbyLabel = new Label("Присоединиться к лобби: ");
        GridPane.setConstraints(joinLobbyLabel, 0, 2);

        // Join Lobby Input
        TextField joinLobbyInput = new TextField();
        GridPane.setConstraints(joinLobbyInput, 1, 2);

        // Join Lobby Button
        Button joinLobbyBtn = new Button("Ок");
        joinLobbyBtn.setOnAction(e -> {
            if (isInt(joinLobbyInput)) {
                GuesserScene.display(stage, Integer.parseInt(joinLobbyInput.getText()), nameInput.getText(),
                        WINDOW_W, WINDOW_H);
            }
        });
        GridPane.setConstraints(joinLobbyBtn, 2, 2);

        grid.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(nameLabel, nameInput, newLobbyLabel, newLobbyInput, newLobbyBtn,
                joinLobbyLabel, joinLobbyInput, joinLobbyBtn);

        Scene scene = new Scene(grid, WINDOW_W, WINDOW_H);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean isInt(TextField input) {
        try {
            int num = Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException e) {
            AlertBox.display("Ошибка","Нужно ввести именно число");
            return false;
        }
    }
}
