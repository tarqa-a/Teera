package com.teera.format;

import com.teera.filework.Pref;
import com.teera.startpoint.InputContentArea;
import com.teera.startpoint.WindowsShowcase;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// Кнопка или просто оболочка?
public class ProgramFormatSetter
{
    public static final double DEFAULT_LEADING = 0;

    private static Button formatSetterButton = action();
    private static int leading;
    private static Label fsLabel;

    private static Button action()
    {
        Button fsButton = new Button("Настройка");
        fsButton.setOnAction(actionEvent ->
        {
            leading = Integer.parseInt(Pref.getPreferences().get(Pref.LEADING, String.valueOf(DEFAULT_LEADING)));
            fsLabel = new Label("Интервал: " + leading);
            fsLabel.setFont(WindowsShowcase.APTOS_FONT);

            Dialog<Void> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();

            VBox vBox = new VBox();
            vBox.setSpacing(10);
            HBox hBox = new HBox();
            hBox.setSpacing(10);

            Button improveButton = new Button("Увеличить");
            Button decreaseButton = new Button("Уменьшить");

            improveButton.setOnAction(ae ->
            {
                if (leading == 30)
                {
                    leading = 0;
                } else
                {
                    leading += 5;
                }

                fsLabel.setText("Интервал: " + leading);
            });

            decreaseButton.setOnAction(ae ->
            {
                if (leading != 0)
                {
                    leading -= 5;
                } else {
                    leading = 30;
                }
                fsLabel.setText("Интервал: " + leading);
            });

            Button applyButton = new Button("Применить");

            applyButton.setOnAction(ae ->
            {
                InputContentArea.setAreaLeading(leading);
                Pref.getPreferences().put(Pref.LEADING, String.valueOf(leading));
                dialog.setResult(null);
                dialog.close();
            });

            WindowsShowcase.defaultStyle(improveButton, decreaseButton, applyButton);

            hBox.getChildren().addAll(improveButton, decreaseButton);
            vBox.getChildren().addAll(fsLabel, hBox, applyButton);
            //vBox.setPrefSize(150, 150);

            dialogPane.setContent(vBox);

            dialogPane.getButtonTypes().add(ButtonType.CLOSE);

            Node stCloseButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            stCloseButton.setVisible(false);
            stCloseButton.setManaged(false);

            dialog.setDialogPane(dialogPane);
            dialog.show();
        });

        return fsButton;
    }

    public static Button getFormatSetterButton()
    {
        return formatSetterButton;
    }
}
