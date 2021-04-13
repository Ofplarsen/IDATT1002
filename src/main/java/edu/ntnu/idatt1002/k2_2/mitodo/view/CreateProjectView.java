package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class CreateProjectView extends View{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;

    private Project parentProject;

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public void save() {
        //TODO: show user that string must be over 1 character
        if (projectTitle.getText().isBlank()) {
            return;
        }

        parentProject.addProject(projectTitle.getText());
        Client.getPrimaryView().updateMainMenu();
    }

    public void exit() {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(Client.getQuickTasks());
    }

    public Node getParent()
    {
        return parent;
    }
}
