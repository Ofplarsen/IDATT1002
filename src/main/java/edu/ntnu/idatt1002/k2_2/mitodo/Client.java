package edu.ntnu.idatt1002.k2_2.mitodo;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.testdata.Default;
import edu.ntnu.idatt1002.k2_2.mitodo.view.PrimaryView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    private static Project rootProject;
    private static PrimaryView primaryView;
    private static Project quickTasks;
    private static View currentView;

    @Override
    public void start(Stage stage) {
        rootProject = null;
        if (rootProject==null)
        {
            rootProject = new Project("Other Projects");
        }

        quickTasks = null;
        if (quickTasks==null)
        {
            quickTasks = new Project("Quick Tasks");
        }

        Default.fillWithTestData(rootProject);
        Default.fillQuickTasksData(quickTasks);

        primaryView = (PrimaryView) FileManager.getView("PrimaryView");

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(Client.getQuickTasks());

        Scene primaryScene = primaryView.getScene();
        stage.setScene(primaryScene);

        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setTitle("MiTodo");
        stage.show();
        stage.setResizable(false); //makes the app look weird if you change around on its size
    }

    @Override
    public void stop()
    {

    }

    public static void main(String[] args) {
        launch();
    }

    public static View setView(String name)
    {
        currentView = FileManager.getView(name);
        primaryView.setContent(currentView);
        return currentView;
    }

    public static PrimaryView getPrimaryView()
    {
        return primaryView;
    }

    public static View getCurrentView()
    {
        return currentView;
    }

    public static Project getRootProject() {
        return rootProject;
    }

    public static Project getQuickTasks(){
        return quickTasks;
    }
}
