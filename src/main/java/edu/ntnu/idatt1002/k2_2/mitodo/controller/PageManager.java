package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.view.Page;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageManager
{
    private Page currentPage;
    private Stage stage;

    public void setPage(Page page)
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

    public Page getPage(String name)
    {
        FXMLLoader fxmlLoader = FileManager.getFXMLLoader(name);
        return fxmlLoader.getController();
    }

    public Page getCurrentPage()
    {
        return currentPage;
    }

    public void setStage(Stage stage)
    {
        stage.setWidth(720);
        stage.setHeight(580);
        stage.setTitle("MiTodo");
        this.stage = stage;
    }

    public Stage getStage()
    {
        return stage;
    }
}
