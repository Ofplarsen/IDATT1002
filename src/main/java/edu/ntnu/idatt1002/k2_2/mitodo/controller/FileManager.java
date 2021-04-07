package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    //Jackson:
    /**
     * File path (this includes double quotation marks, not single) should be on the form: "'file name'.json"
     *
     * TODO I (w) have yet to manage to make a new path and a new file at the same time, plz teach me if you know how.
     *
     * @param fileName name of the file you want to create
     */
    public static boolean createNewFile(String fileName){
        File projectFile = new File(fileName);

        try{ return projectFile.createNewFile(); //Creates a new file if it does not already exist
        }catch (IOException e){ e.printStackTrace(); }
        return false;
    }

    /**
     * Method writes state of a Project object to a JSON file to given file (see createNewFile method for comment on fileName).
     * There is no need for opening or closing any files, the ObjectMapper does this for you.
     *
     * @param project project you want to save as a JSON file
     * @param fileName the file you want to write to
     */
    public static void writeProjectFile(Project project, String fileName){
        ObjectMapper om = new ObjectMapper();

        try {
            om.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for reading from a JSON file (see createNewFile method for comment on fileName), creating and returning
     * a Project object. An IOException is thrown if this fails and null is returned.
     * There is no need for opening or closing any files, the ObjectMapper does this for you.
     *
     * Currently there is a bug with this method where som special characters (e.g. ø) gets decoded as a
     * replacement character, even though encoding them is no problem.
     *
     * @param fileName the file you want to read from
     * @return the file you read from as a Project object or null if this fails
     */
    public static Project readProjectFile(String fileName){
        ObjectMapper om = new ObjectMapper();

        try {
            return om.readValue(new File(fileName), Project.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
