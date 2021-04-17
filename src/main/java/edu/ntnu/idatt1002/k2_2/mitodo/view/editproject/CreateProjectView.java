package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateProjectView extends View
{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;

    private Project parentProject;

    public void setParentProject(Project parentProject)
    {
        this.parentProject = parentProject;
    }

    @FXML
    private void saveAndExit()
    {
        Project project = parentProject.addProject(projectTitle.getText());
        Client.getPrimaryView().updateMainMenu();

        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    @FXML
    private void cancel()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(parentProject);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
