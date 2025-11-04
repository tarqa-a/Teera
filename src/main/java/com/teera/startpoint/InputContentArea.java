package com.teera.startpoint;

import com.teera.filework.UserFileProcessor;
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
        textArea.setFont(WindowsShowcase.APTOS_BOLD_FONT);
        textArea.setWrapText(true);

        textArea.setPrefSize(
                WindowsShowcase.getAreaSceneWidth() * 0.97,
                WindowsShowcase.getAreaSceneHeight() * 0.85);
    }

    /*---------------! Применение интервалов не работает !-------------------*/
    // Изменение интервала для форматирования
    public static void setLeading(int size)
    {
        textArea.setStyle("-fx-line-spacing: " + size + ";");
    }
    /*-----------------------------------------------------------------------*/
}
