package edu.ntnu.idatt1002.k2_2.mitodo;

import edu.ntnu.idatt1002.k2_2.mitodo.data.FontSizeEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Settings;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.testdata.Default;
import edu.ntnu.idatt1002.k2_2.mitodo.util.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.view.Component;
import edu.ntnu.idatt1002.k2_2.mitodo.view.PrimaryView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Stack;

public class Client extends Application {
    private static RootProject rootProject;
    private static PrimaryView primaryView;

    private static Settings settings;

    private static final Stack<View> previousViews = new Stack<>();
    private static View currentView;

    private static final String rootProjectFileName = "rootProject";
    private static final String settingsFileName = "settings";

    @Override
    public void start(Stage stage) {
        rootProject = (RootProject) FileManager.loadSerializableObject(rootProjectFileName);
        if (rootProject == null) {
            rootProject = new RootProject();
        }

        settings = (Settings) FileManager.loadSerializableObject(settingsFileName);
        if (settings == null) {
            settings = new Settings(false, FontSizeEnum.MEDIUM);
        }

        //Default.fillWithTestData(rootProject);

        primaryView = (PrimaryView) getComponent("PrimaryView");

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(rootProject);

        updateMainMenu();

        Scene primaryScene = primaryView.getScene();
        stage.setScene(primaryScene);

        stage.setWidth(1120);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setHeight(640);
        stage.setTitle("MiTodo");
        stage.show();
        stage.getIcons().add(new Image("/images/logo.png"));
    }

    @Override
    public void stop() {
        FileManager.saveSerializableObject(rootProject, rootProjectFileName);
        FileManager.saveSerializableObject(settings, settingsFileName);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setView(View view) {
        if (view.equals(currentView)) return;
        if (currentView != null) {
            previousViews.add(currentView);
        }
        currentView = view;
        primaryView.setContent(view);
        view.getParent().requestFocus();
    }

    public static View setView(String name) {
        View newView = (View) getComponent(name);
        setView(newView);
        return newView;
    }

    public static Component getComponent(String name) {
        return FileManager.getComponent(name);
    }

    public static View returnToPreviousView() {
        View view = previousViews.pop();
        setView(view);
        return currentView;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static PrimaryView getPrimaryView() {
        return primaryView;
    }

    public static void selectCurrentViewInMainMenu() {
        primaryView.selectCurrentViewInMainMenu(currentView);
    }

    public static void updateMainMenu() {
        primaryView.updateMainMenu(currentView);
    }

    public static View getCurrentView() {
        return currentView;
    }

    public static RootProject getRootProject() {
        return rootProject;
    }
}
