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

    public EditProjectView(){
        this.project = new Project("Project title");
    }


    public void save() {
        project.setTitle(projectTitle.getText());
        exit();
    }

    public void exit(){
        Client.setView("ProjectView");
    }

    public void delete(){
        project.removeProjectbyTitle(project.getTitle());
        exit();
    }

    public Node getParent()
    {
        return parent;
    }
}
