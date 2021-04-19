package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class EditProjectView extends View
{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;

    private UserProject project;

    public void setProject(UserProject project)
    {
        Platform.runLater(() -> projectTitle.requestFocus());
        this.project = project;
        projectTitle.setText(project.getTitle());
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
            case DELETE:
                delete();
                break;
        }
    }

    @FXML
    private void saveAndExit()
    {
        try
        {
            project.setTitle(projectTitle.getText());
            Client.getPrimaryView().updateMainMenu();

            Client.returnToPreviousView();
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

    @FXML
    private void cancel()
    {
        Client.returnToPreviousView();
    }

    @FXML
    private void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the project?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(type ->{
            if(type == ButtonType.OK)
            {
                Client.getRootProject().removeProjectFromAll(project.getID());
                Client.getPrimaryView().updateMainMenu();

                ProjectView projectView = (ProjectView) Client.setView("ProjectView");
                projectView.setProject(project.getParent());
            }
        });
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
