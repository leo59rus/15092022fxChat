package ru.gb.koval.javafxchat15092022.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class ChatController {
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageField;
    private final ChatClient client;

    public ChatController() {
        this.client = new ChatClient(this);
        while (true) {
            try {
                client.openConnection();
                break;//если смогли подключтьися то выход с цикла
            } catch (IOException e) {
                //не смогли подключться вывод оштбки
                showNotification();
            }
        }
    }

    private void showNotification() {
        final Alert alert = new Alert(Alert.AlertType.ERROR,
                                      "не могу подключиться к " +
                                              "серверу.\n"
                                              + "Проверьте, что " +
                                              "сервер " +
                                              "запущен/дступен",
                                      new ButtonType("Выйти",
                                                     ButtonBar.ButtonData.CANCEL_CLOSE),
                                      new ButtonType("Попробовать " +
                                                             "снова",
                                                     ButtonBar.ButtonData.OK_DONE)

        );
        alert.setTitle("Ошибка подключения!");
        final Optional<ButtonType> answer = alert.showAndWait();
        final Boolean isExit =
                answer.map(select -> select.getButtonData().isCancelButton()).orElse(false);
        if (isExit) {
            System.exit(0);
        }
    }

    public void clickSendButton() {
        final String message = messageField.getText();
        if (message.isBlank()) {
            return;
        }
        client.sendMessage(message);
        messageField.clear();
        messageField.requestFocus();

    }

    public void addMessage(String message) {
        messageArea.appendText(message = "\n");
    }
}