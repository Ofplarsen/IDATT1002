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
    private Project rootProject;


    //Called for create
    public void setParentProject(Project rootProject)
    {
        this.rootProject = rootProject;
    }

    // Called for edit
    public void setProject(Project project)
    {
        this.project= project;
        projectTitle.setText(project.getTitle());
    }

    public void saveAndExit()
    {
        //TODO: show user that string must be over 1 character
        if (projectTitle.getText().isBlank()) {
            return;
        }

        if(project != null)
        {
            project.setTitle(projectTitle.getText());
        }
        else
        {
            project = rootProject.addProject(projectTitle.getText());
        }

        Client.getPrimaryView().updateMainMenu();
        cancel();
    }

    public void cancel()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        if (project != null)
        {
            projectView.setProject(project);
        }
        else
        {
            projectView.setProject(Client.getQuickTasks());
        }
    }

    public void delete(){
        if(project != null)
        {
            Client.getRootProject().removeFromAll(project.getID());
            Client.getPrimaryView().updateMainMenu();
            project = null;
        }
        cancel();
    }

    public Node getParent()
    {
        return parent;
    }
}
