package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SubProject extends View
{
    @FXML
    private HBox parent;
    @FXML
    private Label projectName;

    private Project project;

    public void setProject(Project project)
    {
        this.project = project;
        projectName.setText(project.getTitle());
    }

    @FXML
    private void handleGoToProjectClick()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
