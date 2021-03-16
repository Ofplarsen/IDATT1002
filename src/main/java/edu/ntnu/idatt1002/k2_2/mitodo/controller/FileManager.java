package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class FileManager
{
    public static DataManager loadDataManager()
    {
        return null;
    }

    public static Settings loadSettings()
    {
        return null;
    }

    public static Parent loadFXML(String name)
    {
        URL url = FileManager.class.getResource("/fxml/" + name + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        try
        {
            return fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
