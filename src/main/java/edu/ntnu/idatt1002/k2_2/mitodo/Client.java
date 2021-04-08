package edu.ntnu.idatt1002.k2_2.mitodo;


//import edu.ntnu.idatt1002.k2_2.mitodo.controller.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.testdata.Default;
import edu.ntnu.idatt1002.k2_2.mitodo.view.EditTaskView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.PrimaryView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    private static Project rootProject;
    private static PrimaryView primaryView;

    public void start(Stage stage) {
        stage.setWidth(1080);
        stage.setHeight(720);
        stage.setTitle("MiTodo");

        rootProject = new Project("application");
        Default.fillWithTestData(rootProject);

        primaryView = (PrimaryView) FileManager.getView("PrimaryView");
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(Client.getRootProject());

        EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
        editTaskView.update();

        Scene primaryScene = primaryView.getScene();
        stage.setScene(primaryScene);
        stage.show();

        //if(!FileManager.createNewFile("rootprojects.json")){ //TODO everything that is commented out is related to JSON
        //    rootProject = FileManager.readProjectFile("rootprojects.json");
        //}
    }

    /*@Override
    public void stop() throws Exception {
        FileManager.writeProjectFile(rootProject, "rootprojects.json");
        super.stop();
    }*/

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
        //if(!FileManager.createNewFile("rootprojects.json")){
        //    rootProject = FileManager.readProjectFile("rootprojects.json");
        //}
        return rootProject;
    }

    public static void setRootProject(Project rootProject) {
        Client.rootProject = rootProject;
    }
}
