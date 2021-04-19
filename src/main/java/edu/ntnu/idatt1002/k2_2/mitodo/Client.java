package edu.ntnu.idatt1002.k2_2.mitodo;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.testdata.Default;
import edu.ntnu.idatt1002.k2_2.mitodo.view.PrimaryView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application
{
    private static RootProject rootProject;
    private static PrimaryView primaryView;

    private static View previousView;
    private static View currentView;

    private static final String rootProjectFileName = "projectManager";

    @Override
    public void start(Stage stage) {
        rootProject = (RootProject) FileManager.loadSerializableObject(rootProjectFileName);
        if (rootProject ==null)
        {
            rootProject = new RootProject();
        }

        //Default.fillWithTestData(rootProject);

        primaryView = (PrimaryView) FileManager.getView("PrimaryView");

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(rootProject);

        Scene primaryScene = primaryView.getScene();
        stage.setScene(primaryScene);

        stage.setWidth(1120);
        stage.setHeight(640);
        stage.setTitle("MiTodo");
        stage.show();
    }

    @Override
    public void stop()
    {
        FileManager.saveSerializableObject(rootProject, rootProjectFileName);
    }

    public static void main(String[] args) {
        launch();
    }

    public static View setView(String name)
    {
        previousView = currentView;
        currentView = FileManager.getView(name);
        primaryView.setContent(currentView);
        currentView.getParent().requestFocus();
        return currentView;
    }

    public static View returnToPreviousView()
    {
        currentView = previousView;
        primaryView.setContent(currentView);
        currentView.getParent().requestFocus();
        currentView.update();
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

    public static View getPreviousView()
    {
        return previousView;
    }

    public static RootProject getRootProject()
    {
        return rootProject;
    }
}
