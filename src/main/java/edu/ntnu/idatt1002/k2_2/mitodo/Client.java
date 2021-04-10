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

    @Override
    public void start(Stage stage) {
        rootProject = FileManager.loadProject("root_project");
        if (rootProject==null)
        {
            rootProject = new Project("Other Projects");
        }

        quickTasks = FileManager.loadProject("quick_tasks");
        if (quickTasks==null)
        {
            quickTasks = new Project("Quick Tasks");
        }

        //Default.fillQuickTasksData(quickTasks);

        primaryView = (PrimaryView) FileManager.getView("PrimaryView");

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(Client.getQuickTasks());

        Scene primaryScene = primaryView.getScene();
        stage.setScene(primaryScene);

        stage.setWidth(1080);
        stage.setHeight(720);
        stage.setTitle("MiTodo");
        stage.show();
    }

    @Override
    public void stop()
    {
        FileManager.saveProject(rootProject, "root_project");
        FileManager.saveProject(quickTasks, "quick_tasks");
    }

    public static void main(String[] args) {
        launch();
    }

    public static View setView(String name)
    {
        View view = FileManager.getView(name);
        primaryView.setContent(view);
        return view;
    }

    public static PrimaryView getPrimaryView()
    {
        return primaryView;
    }

    public static Project getRootProject() {
        return rootProject;
    }

    public static Project getQuickTasks(){
        return quickTasks;
    }
}
