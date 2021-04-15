package edu.ntnu.idatt1002.k2_2.mitodo.file;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class with static methods for loading and saving files.
 */
public class FileManager
{
    private static final String FILES_PATH = "src/main/resources/files/";

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

    public static void saveProject(Project project, String name)
    {
        File projectFile = new File(FILES_PATH + name);

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

    public static Project loadProject(String name)
    {
        File projectFile = new File(FILES_PATH + name);

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

    //JSON (excluding createNewFile)

    public static boolean createNewFile(String fileName){
        File projectFile = new File(FILES_PATH + fileName);

        try{ return projectFile.createNewFile(); //Creates a new file if it does not already exist
        }catch (IOException e){ e.printStackTrace(); }
        return false;
    }

    public static void writeProjectFile(Project project, String fileName){
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules(); //Need this to make the ObjectMapper understand we are dealing with LocalDate

        try {
            om.writerWithDefaultPrettyPrinter().writeValue(new File(FILES_PATH + fileName), project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Project readProjectFile(String fileName){
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules(); //Need this to make the ObjectMapper understand we are dealing with LocalDate
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //To make everything not act up when reading from file

        try {
            return om.readValue(new File(FILES_PATH + fileName), Project.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
