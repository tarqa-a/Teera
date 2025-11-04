package com.teera.filework;

import com.teera.startpoint.WindowsShowcase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.CompletableFuture;


public class UnsaveOpen
{

    public CompletableFuture<Boolean> confirmUnsave() throws InterruptedException
    {
        CompletableFuture<Boolean> task = new CompletableFuture<>();

        // Проверка на несохраненные изменения
        if (!Pref.getPreferences().get(Pref.FILE_CONTENT, "").isEmpty())
        {

            Dialog<Void> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();

            VBox vBox = new VBox();
            vBox.setSpacing(10);

            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.BOTTOM_CENTER);

            Button unsaveButton = new Button("Не сохранять");
            Button cancelButton = new Button("Отмена");

            WindowsShowcase.defaultStyle(unsaveButton, cancelButton);

            unsaveButton.setOnAction(ae ->
            {
                dialog.setResult(null);
                dialog.close();

                task.complete(true);
            });

            cancelButton.setOnAction(ae ->
            {
                dialog.setResult(null);
                dialog.close();

                task.complete(false);
            });

            // Здесь можно сделать флажок "больше не спрашивать"
            hBox.getChildren().addAll(unsaveButton, cancelButton);
            Label label = new Label("Изменения не сохранятся. Выйти?");
            label.setFont(WindowsShowcase.APTOS_BOLD_FONT);
            vBox.getChildren().addAll(label, hBox);

            dialogPane.setContent(vBox);

            dialogPane.getButtonTypes().add(ButtonType.CLOSE);
            Node stCloseButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            stCloseButton.setVisible(false);
            stCloseButton.setManaged(false);

            dialog.setDialogPane(dialogPane);
            dialog.show();
        } else {
            task.complete(true);
        }

        return task;
    }
}
