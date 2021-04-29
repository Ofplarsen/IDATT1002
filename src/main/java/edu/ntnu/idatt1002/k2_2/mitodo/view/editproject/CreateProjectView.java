package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.util.SoundEffects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 * Class representing the view for creating a new user project
 * Linked with the CreateProjectView.fxml file.
 *
 * @version 1.0.0
 */
public class CreateProjectView extends EditOrCreateProjectView
{
    @FXML
    private Label header;

    private Project parentProject;

    /**
     * Sets the parent project of the new project.
     * @param parentProject The parent project.
     */
    public void setParentProject(Project parentProject)
    {
        Platform.runLater(() -> projectTitle.requestFocus());
        this.parentProject = parentProject;
        if(parentProject.equals(Client.getRootProject())){ header.setText("Creating New Project"); }
        else header.setText("Creating New Project in " + parentProject.getTitle());
    }

    /**
     * Saves the project and goes to project view with the new project.
     * Displays an popup alert window if the user wrote any illegal input.
     */
    @FXML
    @Override
    protected void saveAndExit()
    {
        try
        {
            UserProject project = parentProject.addProject(projectTitle.getText());
            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(project);
            Client.updateMainMenu();
        }
        catch (IllegalArgumentException e)
        {
            SoundEffects.playErrorSound();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public String getMainMenuTitle()
    {
        return "Create New Project";
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof CreateProjectView;
    }
}
