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

    @Override
    public void start(Stage stage) {
        rootProject = FileManager.loadProject();
        if (rootProject==null)
        {
            rootProject = new Project("Quick tasks");
        }

        //Default.fillWithTestData(rootProject);

        primaryView = (PrimaryView) FileManager.getView("PrimaryView");

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(rootProject);

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
        FileManager.saveProject(rootProject);
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
}
