package edu.ntnu.idatt1002.k2_2.mitodo;


//import edu.ntnu.idatt1002.k2_2.mitodo.controller.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.controller.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.controller.PageManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.testdata.Default;
import edu.ntnu.idatt1002.k2_2.mitodo.view.Primary;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    private static Project rootProject;
    private PageManager pageManager;

    public void start(Stage stage) {
        rootProject = new Project("application");

        //if(!FileManager.createNewFile("rootprojects.json")){ //TODO everything that is commented out is related to JSON
        //    rootProject = FileManager.readProjectFile("rootprojects.json");
        //}
        Default.fillWithTestData(rootProject);

        this.pageManager = new PageManager();

        pageManager.setStage(stage);
        Primary pageTestPage = (Primary) pageManager.getPage("Primary");
        pageManager.setPage(pageTestPage);
    }

    /*@Override
    public void stop() throws Exception {
        FileManager.writeProjectFile(rootProject, "rootprojects.json");
        super.stop();
    }*/

    public static void main(String[] args) {
        launch();
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
