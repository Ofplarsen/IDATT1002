package edu.ntnu.idatt1002.k2_2.mitodo;


import edu.ntnu.idatt1002.k2_2.mitodo.controller.PageManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.testdata.Default;
import edu.ntnu.idatt1002.k2_2.mitodo.view.AllProjects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.PageTest;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    private static Project rootProject;
    private PageManager pageManager;

    public void start(Stage stage) {
        rootProject = new Project("application");
        Default.fillWithTestData(rootProject);

        this.pageManager = new PageManager();

        pageManager.setStage(stage);
        PageTest pageTestPage = (PageTest) pageManager.getPage("AllProjects");
        pageManager.setPage(pageTestPage);

        AllProjects allProjectsPage = (AllProjects) pageManager.getPage("AllProjects");
        pageManager.setPage(allProjectsPage);
    }

    public static void main(String[] args) {
        launch();
    }

    public static Project getRootProject() {
        return rootProject;
    }

    public static void setRootProject(Project rootProject) {
        Client.rootProject = rootProject;
    }
}
