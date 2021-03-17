package edu.ntnu.idatt1002.k2_2.mitodo;

import edu.ntnu.idatt1002.k2_2.mitodo.controller.DataManager;
import edu.ntnu.idatt1002.k2_2.mitodo.controller.PageManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.QuickTasks;
import edu.ntnu.idatt1002.k2_2.mitodo.view.*;

import edu.ntnu.idatt1002.k2_2.mitodo.testdata.TestData;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application
{
    @Override
    public void start(Stage stage)
    {
        TestData.fillWithTestData();

        SubprojectPage quickTasksPage = SubprojectPage.getNewPage(DataManager.getQuickTasks());

        PageManager.setStage(stage);
        PageManager.setPage(quickTasksPage);
    }

    public static void main(String[] args)
    {
        launch();
    }
}