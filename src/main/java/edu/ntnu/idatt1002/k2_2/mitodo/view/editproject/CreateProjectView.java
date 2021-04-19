package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    public void keyListener(KeyEvent keyEvent){
        switch (keyEvent.getCode()){
            case ENTER:
                saveAndExit();
                break;
            case ESCAPE:
                cancel();
                break;
        }
    }

    @FXML
    private void saveAndExit()
    {
        try
        {
            UserProject project = parentProject.addProject(projectTitle.getText());
            Client.getPrimaryView().updateMainMenu();

            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(project);
        }
        catch (IllegalArgumentException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel()
    {
        Client.returnToPreviousView();
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
