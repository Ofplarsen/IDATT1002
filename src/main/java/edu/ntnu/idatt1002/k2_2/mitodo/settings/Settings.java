package edu.ntnu.idatt1002.k2_2.mitodo.settings;

import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;

public class Settings
{
    private int fontSize = 16;

    private static Settings instance;

    private Settings()
    {

    }

    public static Settings get()
    {
        if (instance == null)
        {
            instance = FileManager.loadSettings();
        }
        if (instance == null)
        {
            instance = new Settings();
        }
        return instance;
    }

    public static int getFontSize()
    {
        return get().fontSize;
    }

    public void setFontSize(int fontSize)
    {
        get().fontSize = fontSize;
    }
}
