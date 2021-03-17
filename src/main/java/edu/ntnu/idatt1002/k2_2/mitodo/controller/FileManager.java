package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import javafx.fxml.FXMLLoader;
import java.net.URL;

public class FileManager
{
    public static FXMLLoader getFXMLLoader(String name)
    {
        URL url = FileManager.class.getResource("/fxml/" + name + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        try
        {
            fxmlLoader.load();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fxmlLoader;
    }
}
