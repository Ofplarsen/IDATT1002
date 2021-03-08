package edu.ntnu.idatt1002.k2_2.mitodo;

import edu.ntnu.idatt1002.k2_2.mitodo.scene.*;

import edu.ntnu.idatt1002.k2_2.mitodo.testdata.TestData;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        TestData.fillWithTestData();

        SceneManager.setStage(stage);
        SceneManager.setPage(new QuickTasksPage());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}