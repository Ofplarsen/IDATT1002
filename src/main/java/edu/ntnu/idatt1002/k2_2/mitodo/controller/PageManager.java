package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.view.Page;
import edu.ntnu.idatt1002.k2_2.mitodo.view.SubprojectPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class with static methods for managing pages.
 */
public class PageManager
{
    private static Page currentPage;
    private static Stage stage;
    private static SubprojectPage subprojectPage;

    /**
     * Sets the page to the given page.
     * @param page
     */
    public static void setPage(Page page)
    {
        if (stage == null)
        {
            throw new IllegalStateException("PageManager.setStage must be called before setPage can be called.");
        }

        currentPage = page;

        Scene scene = page.getScene();
        stage.setScene(scene);

        stage.show();

        page.fillWithContent();
    }

    /**
     * Gets an instance of the fx:controller page-class defined in the FXML-file from the path "/fxml/{name}.fxml".
     * @param name The name of the file without the file-ending ".fxml".
     * @return The instance of the page-class.
     */
    public static Page getPage(String name)
    {
        FXMLLoader fxmlLoader = FileManager.getFXMLLoader(name);
        return fxmlLoader.getController();
    }

    /**
     * @return The current page.
     */
    public static Page getCurrentPage()
    {
        return currentPage;
    }

    /**
     * Sets the stage to the given stage.
     * @param stage
     */
    public static void setStage(Stage stage)
    {
        stage.setWidth(720);
        stage.setHeight(580);
        stage.setTitle("MiTodo");
        PageManager.stage = stage;
    }

    /**
     * @return The stage.
     */
    public static Stage getStage()
    {
        return stage;
    }
}
