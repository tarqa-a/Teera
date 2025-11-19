package com.teera.startpoint;

import com.teera.filework.*;
import com.teera.filework.UserFileProcessor;
import com.teera.format.ProgramFormatSetter;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Teera - basic text editor (study project).
 *
 * @author tarqa
 * @version 1.0 02-11-2025
 */

public class WindowsShowcase extends Application
{
    public static final Font APTOS_FONT = Font.font("Aptos", 16);

    private static Stage mainstage;

    // Настройки окна можно далее сделать более гибкими.
    private static int areaSceneWidth = 764, areaSceneHeight = 432;

    public static int getAreaSceneHeight()
    {
        return areaSceneHeight;
    }

    public static int getAreaSceneWidth()
    {
        return areaSceneWidth;
    }

    @Override
    public void init() throws Exception
    {
        // Если файл пустой, а содержание нет - мы сохраняем содержание,
        // Если файл непустой, то и содержание точно не пустое,
        // поскольку файл мы всегда читаем;
        // Pref - простой класс для работы с хранилищем, Keys - перечисление
        // ключей Preferences. Также будут ключи настроек программы;
        // Пока делаем с Preferences, поскольку это проще!
        // Делать внешний конфиг настроек будем в другом проекте!
        String path = Pref.getPreferences().get(Pref.FILE_PATH, "");
        if (path != null) UserFileProcessor.init(new File(path));

        String content = Pref.getPreferences().get(Pref.FILE_CONTENT, "");
        if (content != null) UserFileProcessor.updateContent(content);

        // InputContentArea - простой класс для создания поля ввода.
        InputContentArea.init();

        InputContentArea.getArea().textProperty()
                .addListener(new InvalidationListener()
                {
                    @Override
                    public void invalidated(Observable observable)
                    {
                        String content = InputContentArea.getArea().getText();

                        UserFileProcessor.updateContent(content);

                        // Устанавливаем файл в настройках (будет убран при сохранении)
                        Pref.getPreferences().put(Pref.FILE_PATH, UserFileProcessor.getUserFilePath());
                        // Любые изменения в поле ввода уже считаются несохраненными изменениями
                        Pref.getPreferences().put(Pref.FILE_CONTENT, content);

                        // Изменения обозначаем звездочкой
                        if (getStage() != null && !getStage().getTitle().contains("*")) WindowsShowcase.getStage().setTitle("*" + getStage().getTitle());
                    }
                });

        // Стиль поля ввода создается отдельным методом, поскольку
        // оно заключено в свою статическую оболочку (в отличие от кнопок);
        InputContentArea.setDefaultStyle();
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        String title = UserFileProcessor.getUserFileName();
        if (title.isEmpty())
        {
            stage.setTitle("Безымянный");
        } else
        {
            stage.setTitle(title);
        }
        stage.setResizable(false);

        GridPane nodeRoot = new GridPane(10, 10);
        Scene areaScene = new Scene(nodeRoot, areaSceneWidth, areaSceneHeight);
        stage.setScene(areaScene);

        // Настройка главных кнопок
        Button openButton = new Button("Открыть");
        Button saveButton = new Button("Сохранить");
        Button closeButton = new Button("Закрыть");

        // Стандартный стиль кнопок, пока форматирование
        // связано только с интервалами
        defaultStyle(openButton, closeButton, saveButton, ProgramFormatSetter.getFormatSetterButton());

        // Пока кнопок не много ограничусь ручной инициализацией действий,
        // позже можно будет изменить процесс назначения действий
        openButton.setOnAction(actionEvent ->
        {
            try
            {
                OpenFile.open();
            } catch (IOException | InterruptedException | ExecutionException e)
            {
                throw new RuntimeException(e);
            }
        });

        closeButton.setOnAction(actionEvent ->
        {
            try
            {
                CloseFile.close();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });

        saveButton.setOnAction(actionEvent ->
        {
            try
            {
                SaveFile.save();
            } catch (IOException | InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });


        // Панель для кнопок
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.getChildren().addAll(openButton, closeButton, saveButton, ProgramFormatSetter.getFormatSetterButton());

        // Добавляем объекты в главное окно
        nodeRoot.add(hBox, 1, 1);
        nodeRoot.add(InputContentArea.getArea(), 1, 2);

        // Stage нам потребуется для вывода FileChooser
        mainstage = stage;

        stage.show();
    }

    public static Stage getStage()
    {
        return mainstage;
    }

    // Нет конфига настроек, можно сделать подключаемый форматтер в качестве первого
    // аргумента
    public static void defaultStyle(Button... buttons)
    {
        List<Button> list = Arrays.asList(buttons);
        Iterator<Button> iterator = list.iterator();

        while (iterator.hasNext())
        {
            Button el = iterator.next();
            el.setFont(APTOS_FONT);
            el.setFocusTraversable(false);
        }

    }

}
