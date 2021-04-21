package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.util.SoundEffects;
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
