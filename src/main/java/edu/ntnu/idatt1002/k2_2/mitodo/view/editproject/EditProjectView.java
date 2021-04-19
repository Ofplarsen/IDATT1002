package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
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

import java.util.Locale;

public class EditProjectView extends View
{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;

    private Project parentProject;
    private Project project;

    public void setParentProject(Project parentProject)
    {
        this.parentProject = parentProject;
    }

    public void setProject(Project project)
    {
        this.project = project;
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
        try {
            project.setTitle(projectTitle.getText());
            Client.getPrimaryView().updateMainMenu();

            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(project);
        }catch (IllegalArgumentException e){
            if(project.getTitle().equalsIgnoreCase(projectTitle.getText())){

                cancel();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancel()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    @FXML
    private void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the project?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(type ->{
            if(type == ButtonType.OK){
                Client.getRootProject().removeProjectFromAll(project.getID());
                Client.getPrimaryView().updateMainMenu();

                ProjectView projectView = (ProjectView) Client.setView("ProjectView");
                projectView.setProject(Client.getQuickTasks());
            }else if(type == ButtonType.CANCEL){

            }
        });
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
