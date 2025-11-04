package com.teera.filework;

import java.util.prefs.Preferences;

public class Pref
{
    public static final String FILE_PATH = "teeraFilePath";
    public static final String FILE_CONTENT = "teeraFileContent";
    public static final String LEADING = "teeraTextAreaLeading";

    private static Preferences preferences = Preferences.userRoot();

    public static Preferences getPreferences()
    {
        return preferences;
    }

}