package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class CreateProjectView extends View
{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;
    @FXML
    private Button btnSaE;

    private Project parentProject;

    public void setParentProject(Project parentProject)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                projectTitle.requestFocus();
            }
        });
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
        projectTitle.addEventFilter(KeyEvent.KEY_PRESSED, keyEventTitle ->{
            if(keyEventTitle.getCode() == KeyCode.DOWN){
                btnSaE.requestFocus();
            }
        });
    }

    @FXML
    private void saveAndExit()
    {
        try {
            Project project = parentProject.addProject(projectTitle.getText());
            Client.getPrimaryView().updateMainMenu();

            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(project);
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
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
