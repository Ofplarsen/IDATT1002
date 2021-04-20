package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;

import edu.ntnu.idatt1002.k2_2.mitodo.effects.SoundEffects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CreateTaskView extends EditOrCreateTaskView
{
    public void setProject(Project project)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taskName.requestFocus();
            }
        });
        this.project = project;
    }

    @FXML
    protected void saveAndExit()
    {
        try {
            this.task = project.addTask(taskName.getText());
            super.saveAndExit();
        }catch(IllegalArgumentException e){
            SoundEffects.playErrorSound();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
