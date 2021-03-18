package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import javafx.fxml.FXMLLoader;
import java.net.URL;

/**
 * Class with static methods for loading and saving files.
 */
public class FileManager
{
    /**
     * Gets a loaded FXMLLoader from the path "/fxml/{name}.fxml"
     * @param name The name of the file without the file-ending ".fxml".
     * @return The loaded FXMLLoader. null if not found.
     */
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
            return null;
        }

        return fxmlLoader;
    }
}
