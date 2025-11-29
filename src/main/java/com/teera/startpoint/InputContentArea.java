package com.teera.startpoint;

import static com.teera.debug.ProgramLog.logger;

import javafx.geometry.Insets;
import org.fxmisc.richtext.InlineCssTextArea;
import com.teera.filework.Pref;
import com.teera.filework.UserFileProcessor;
import com.teera.format.ProgramFormatSetter;

public class InputContentArea
{
    private static InlineCssTextArea styledTextArea;

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

        styledTextArea = new InlineCssTextArea(text);
    }

    // Добавление textArea в основное окно
    public static InlineCssTextArea getArea()
    {
        return styledTextArea;
    }

    // Установка стиля по умолчанию, пока форматирование только меняет интервалы
    public static void setDefaultStyle()
    {
        styledTextArea.setWrapText(true);

        styledTextArea.setPrefHeight(WindowsShowcase.getAreaSceneHeight() * 0.85);
        styledTextArea.setPrefWidth(WindowsShowcase.getAreaSceneWidth() * 0.97);

        setAreaLeading(Integer.parseInt(Pref.getPreferences().get(Pref.LEADING, String.valueOf(ProgramFormatSetter.DEFAULT_LEADING))));

        styledTextArea.requestLayout();
    }

    public static void setAreaLeading(int size)
    {
        String spacingCommand = "-fx-line-spacing: " + size + "px;";
        String fontCommand = "-fx-font-family: Aptos, sans-serif;\n-fx-font-size: 16px;";

        styledTextArea.setPadding(new Insets(10, 0, 0, 10));

        styledTextArea.setParagraphInsertionStyle(spacingCommand);
        styledTextArea.setStyle(fontCommand);

        styledTextArea.replaceText(UserFileProcessor.getContent().toString());
    }
}
