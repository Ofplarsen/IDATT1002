package edu.ntnu.idatt1002.k2_2.mitodo.view.editproject;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.EditTaskView;
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

public class EditProjectView extends View
{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;
    @FXML
    private Button btnSaE;

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
            final int projectTitleMaxLength = 28;
            if (projectTitle.getText().length() > projectTitleMaxLength) throw new IllegalArgumentException("Project Name must be below " + projectTitleMaxLength);
            else project.setTitle(projectTitle.getText());
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
                ProjectView projectView = (ProjectView) Client.setView("ProjectView");
                projectView.setProject(project.getParent());
                Client.updateMainMenu();
            }
        });
    }

    @Override
    public Node getParent()
    {
        return parent;
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
