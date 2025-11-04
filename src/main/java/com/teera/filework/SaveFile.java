package com.teera.filework;

import com.teera.startpoint.WindowsShowcase;

import java.io.IOException;

public class SaveFile
{

    public static void save() throws IOException, InterruptedException
    {
        // Проверка наличия файла сохранения
        if (UserFileProcessor.getUserFileName().isEmpty())
        {
            UserFileProcessor.init(OpenFile.chooseFile());
        }

        // Задача выполнена!
        if (!UserFileProcessor.getUserFileName().isEmpty())
        {
            UserFileProcessor.write(UserFileProcessor.getContent().toString());

            // Убираем значок несохраненных изменений
            WindowsShowcase.getStage().setTitle(UserFileProcessor.getUserFileName());

            Pref.getPreferences().put(Pref.FILE_PATH, "");
            Pref.getPreferences().put(Pref.FILE_CONTENT, "");
        }
    }
}
