package edu.ntnu.idatt1002.k2_2.mitodo.file;

import edu.ntnu.idatt1002.k2_2.mitodo.view.Component;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;

import javafx.fxml.FXMLLoader;
import java.io.*;
import java.net.URL;

/**
 * Class with static methods for loading and saving files.
 */
public class FileManager
{
    public static final File SAVE_DIR = new File(new File(System.getProperty("user.home")), ".mitodo");

    /**
     * Gets a loaded FXMLLoader from the path "/fxml/{name}.fxml"
     * @param name The name of the file without the file-ending ".fxml".
     * @return The loaded FXMLLoader. null if not found.
     */
    public static Component getComponent(String name)
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

        return fxmlLoader.getController();
    }

    public static void saveSerializableObject(Serializable object, String name)
    {
        File file = new File(SAVE_DIR, name);

        try
        {
            SAVE_DIR.mkdir();
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        (
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
        )
        {
            os.writeObject(object);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Serializable loadSerializableObject(String name)
    {
        File file = new File(SAVE_DIR, name);

        if (!file.isFile())
        {
            return null;
        }

        Serializable serializable;
        try
        (
            FileInputStream fs = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fs);
        )
        {
            serializable = (Serializable) is.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return serializable;
    }
}
