package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import javafx.stage.Stage;

public class SceneManager
{
    private Scene currentScene;
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

    public static void setScene(Scene scene)
    {
        get().currentScene = scene;
        get().currentScene.show();
    }

    public static Scene getCurrentScene()
    {
        return get().currentScene;
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
