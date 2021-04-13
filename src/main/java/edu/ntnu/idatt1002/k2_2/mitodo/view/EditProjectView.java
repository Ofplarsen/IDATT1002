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

    public void setProject(Project project){
        this.project= project;
        projectTitle.setText(project.getTitle());}

    public EditProjectView(){
        this.project = new Project("Project title");
    }


    public void save() {
        //TODO: show user that string must be over 1 character
        if (projectTitle.getText().isBlank()) {
            return;
        }
        Client.getRootProject().getProject(project.getID()).setTitle(projectTitle.getText());
        Client.getPrimaryView().updateMainMenu();
        exit();
    }

    public void exit() {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    public void delete(){
        //TODO: Create "delete from all"-method in Project and use that instead
        Client.getRootProject().removeProject(project.getID());
        Client.getPrimaryView().updateMainMenu();

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(Client.getQuickTasks());
    }

    public Node getParent()
    {
        return parent;
    }
}
