package com.teera.filework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * UserFileProcessor выполняет доступ к файлу пользователя
 * Сейчас механизм сырой, поскольку программа не может обрабатывать большие объемы
 * данных. Выдает ошибку, если открыть файл больше обычного (например, "массив_данных").
 */
public class UserFileProcessor
{
    /// Поле файла пользователя
    private static File userFile;

    /// Поле содержания файла пользователя
    private static Appendable userFileContent;

    /**
     * Инициализация происходит при открытии файла, поэтому она происходит не сразу.
     *
     * @param file является файлом, указанным пользователем.
     */
    public static void init(File file)
    {
        userFile = file;
    }

    /**
     * Чтение происходит после открытия файла.
     *
     * @return userFileContent возвращает объект для обработки содержания файла;
     */
    public static Appendable read() throws IOException, InterruptedException
    {
        userFileContent = new StringBuilder();

        Thread thread = new Thread(() ->
        {
            try (Scanner scanner = new Scanner(userFile, StandardCharsets.UTF_8))
            {
                while (scanner.hasNext()) userFileContent.append(scanner.nextLine() + "\n");

            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });

        thread.start();
        thread.join();

        return userFileContent;
    }

    /**
     * Запись в файл является частью процесса сохранения файла.
     * Если файл не будет заранее открыт,
     * потребуется его сначала открыть (или создать).
     *
     * @param content является строчным представлением нового содержания файла,
     *                то есть измененный userFileContent; Внешнее сохранение в preferences
     *                никогда не записывается в файл. Допустимо только инициализировать
     *                userFileContent значением из памяти, но никак не записывать;
     */
    public static void write(String content) throws IOException, InterruptedException
    {
        Thread thread = new Thread(() ->
        {
            try (FileWriter fileWriter = new FileWriter(userFile))
            {
                fileWriter.write(content + "\n");

            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });

        thread.start();
        thread.join();
    }

    /**
     * @return возвращает текущее содержание файла. Может потребоваться при
     * сохранении файла, поскольку заглавный объект userFileContent остался
     * в файле чтения файла. Чтобы его не передавать можно воспользоваться этим методом;
     */
    public static Appendable getContent()
    {
        return userFileContent;
    }

    /**
     * Данный метод обновляет текущее содержание файла без записи
     * (потенциальное содержание);
     *
     * @param str новое содержание. Подразумевается, что метод будет работать с
     *            получением текста из поля ввода TextArea в оперативном режиме;
     */
    public static void updateContent(String str)
    {
        userFileContent = new StringBuilder(str);
    }

    /**
     * @return currentFileName возвращает имя текущего файла, либо ничего не возвращает;
     * Можно использовать для обновления заголовка окна и быстрой проверки наличия файла;
     */
    public static String getUserFileName()
    {
        if (userFile != null)
        {
            return userFile.getName();
        } else
        {
            return "";
        }
    }

    public static String getUserFilePath()
    {
        if (userFile != null)
        {
            return userFile.getAbsolutePath();
        } else
        {
            return "";
        }
    }
}
