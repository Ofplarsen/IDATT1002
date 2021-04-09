package edu.ntnu.idatt1002.k2_2.mitodo.file;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.net.URL;

/**
 * Class with static methods for loading and saving files.
 */
public class FileManager
{
    private static final String PATH = "src/main/resources/files/root_project";

    /**
     * Gets a loaded FXMLLoader from the path "/fxml/{name}.fxml"
     * @param name The name of the file without the file-ending ".fxml".
     * @return The loaded FXMLLoader. null if not found.
     */
    public static View getView(String name)
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

    public static void saveProject(Project project)
    {
        File projectFile = new File(PATH);

        try
        {
            projectFile.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        (
            FileOutputStream fs = new FileOutputStream(projectFile);
            ObjectOutputStream os = new ObjectOutputStream(fs);
        )
        {
            os.writeObject(project);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Project loadProject()
    {
        File projectFile = new File(PATH);
        if (!projectFile.isFile())
        {
            return null;
        }

        Project project = null;
        try
        (
            FileInputStream fs = new FileInputStream(projectFile);
            ObjectInputStream is = new ObjectInputStream(fs);
        )
        {
            project = (Project) is.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return project;
    }
}
