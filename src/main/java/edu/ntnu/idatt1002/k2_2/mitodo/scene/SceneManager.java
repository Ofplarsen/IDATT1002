package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager
{
    private Page currentPage;
    private Stage stage;

    private static SceneManager instance;

    private SceneManager()
    {

    }

    public static SceneManager get()
    {
        if (instance == null)
        {
            instance = new SceneManager();
        }
        return instance;
    }

    public static void setPage(Page page)
    {
        get().currentPage = page;
        Scene scene = get().currentPage.getScene();
        get().stage.setScene(scene);
    }

    public static Page getCurrentPage()
    {
        return get().currentPage;
    }

    public static void setStage(Stage stage)
    {
        stage.setWidth(720);
        stage.setHeight(580);
        get().stage = stage;
    }

    public static Stage getStage()
    {
        return get().stage;
    }
}
