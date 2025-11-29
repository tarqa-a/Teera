package com.teera.filework;

import com.teera.startpoint.InputContentArea;
import com.teera.startpoint.WindowsShowcase;

import java.util.concurrent.CompletableFuture;

public class CloseFile
{
    public static void close() throws InterruptedException
    {
        if (UserFileProcessor.getUserFileName().isEmpty() &&
                UserFileProcessor.getContent().toString().isEmpty())
        {
            return;
        }

        UnsaveOpen unsaveOpen = new UnsaveOpen();

        // Сначала запускаем диалоговое окно
        CompletableFuture<Boolean> confirmTask = unsaveOpen.confirmUnsave();

        // Только получив положительный ответ, мы можем перейти к закрытию файла
        confirmTask.thenAccept(result ->
        {
            if (result)
            {
                // Устанавливаем на место поля ввода
                InputContentArea.getArea().clear();
                UserFileProcessor.init(null);

                // Устанавливаем название
                WindowsShowcase.getStage().setTitle("Безымянный");

                // Очищаем, чтобы не было несохраненных изменений
                Pref.getPreferences().put(Pref.FILE_PATH, "");
                Pref.getPreferences().put(Pref.FILE_CONTENT, "");
            }
        });
    }
}
