package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.view.Page;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageManager
{
    private static Page currentPage;
    private static Stage stage;

    public static void setPage(Page page)
    {
        currentPage = page;

        Scene scene = page.getScene();
        stage.setScene(scene);

        stage.show();

        page.fillWithContent();
    }

    public static Page getCurrentPage()
    {
        return currentPage;
    }

    public static void setStage(Stage stage)
    {
        stage.setWidth(720);
        stage.setHeight(580);
        PageManager.stage = stage;
    }

    public static Stage getStage()
    {
        return stage;
    }
}
