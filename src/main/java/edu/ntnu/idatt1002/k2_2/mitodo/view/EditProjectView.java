package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditProjectView extends View{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;

    private Project project;


    public EditProjectView(Project project) {
        this.project = project;
    }

    public void setProject(Project project){ this.project= project; }

    public EditProjectView(){
        this.project = new Project("Project title");
    }


    public void save() {
        project.setTitle(projectTitle.getText());
        exit();
    }

    public void exit(){ //Returns to the same project
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    public void delete(){ //denna fjerne ikkje prosjektet forresten, må sikkert gå via rootProject for å fjerna
        project.removeProject(project.getID());
        //exit();
        ProjectView projectView = (ProjectView) Client.setView("ProjectView"); //returns to quick tasks because it can not return to deleted project
        projectView.setProject(Client.getQuickTasks());
    }

    public Node getParent()
    {
        return parent;
    }
}
