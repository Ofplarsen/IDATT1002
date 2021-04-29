package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Class representing the view for editing an existing user project
 * Linked with the EditProjectView.fxml file.
 *
 * @version 1.0.0
 */
public class EditProjectView extends EditOrCreateProjectView
{
    private UserProject project;

    /**
     * Sets the project to edit.
     * @param project The project.
     */
    public void setProject(UserProject project)
    {
        this.project = project;
        projectTitle.setText(project.getTitle());
        Platform.runLater(() -> projectTitle.requestFocus());
    }

    /**
     * Handles "add subproject" button clicks.
     */
    @FXML
    private void handleAddSubproject()
    {
        CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
        createProjectView.setParentProject(project);
    }

    /**
     * Saves the changes and goes back to the previous view.
     * Displays an popup alert window if the user wrote any illegal input.
     */
    @FXML
    @Override
    protected void saveAndExit()
    {
        try
        {
            project.setTitle(projectTitle.getText());
            Client.returnToPreviousView();
            Client.updateMainMenu();
        }
        catch (IllegalArgumentException e)
        {
            if(project.getTitle().equalsIgnoreCase(projectTitle.getText()))
            {
                cancel();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Asks the user with a popup alert window for confirmation and then
     * deletes the project and goes to project view with the parent project.
     */
    @FXML
    private void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the project?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(type ->
        {
            if(type == ButtonType.OK)
            {
                Client.getRootProject().removeProjectFromAll(project.getID());
                ProjectView projectView = (ProjectView) Client.setView("ProjectView");
                projectView.setProject(project.getParent());
                Client.updateMainMenu();
            }
        });
    }

    @Override
    public String getMainMenuTitle()
    {
        return "Edit Project";
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof EditProjectView && ((EditProjectView) view).project.equals(project);
    }
}
