package com.teera.startpoint;

import com.teera.debug.ProgramLog;
import com.teera.filework.Pref;
import com.teera.filework.UserFileProcessor;
import com.teera.format.ProgramFormatSetter;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
public class InputContentArea
{
    private static TextArea textArea;

    // Инициализация как только сможем загрузить содержание файла
    public static void init()
    {
        String text;
        if (UserFileProcessor.getContent().toString() == null)
        {
            text = "";
        } else
        {
            text = UserFileProcessor.getContent().toString();
        }
        textArea = new TextArea(text);
    }

    // Добавление textArea в основное окно
    public static TextArea getArea()
    {
        return textArea;
    }

    // Установка стиля по умолчанию, пока форматирование только меняет интервалы
    public static void setDefaultStyle()
    {
        textArea.setFont(WindowsShowcase.APTOS_FONT);
        textArea.setWrapText(true);

        textArea.setPrefHeight(WindowsShowcase.getAreaSceneHeight() * 0.85);
        textArea.setPrefWidth(WindowsShowcase.getAreaSceneWidth() * 0.97);

        setAreaLeading(Double.parseDouble(Pref.getPreferences().get(Pref.LEADING, String.valueOf(ProgramFormatSetter.DEFAULT_LEADING))));
    }

    public static void setAreaLeading(double size)
    {
        textArea.setStyle("-fx-line-spacing: " + size + "px;");

        Node text = textArea.lookup(".text");
        if (text != null)
        {
            text.setStyle("-fx-line-spacing: " + size + "px;");
        }

        textArea.requestLayout();
    }
}
