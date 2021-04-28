package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;

import edu.ntnu.idatt1002.k2_2.mitodo.util.SoundEffects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class EditTaskView extends EditOrCreateTaskView
{

    /**
     * Sets the task to edit.
     * @param task The task.
     */
    public void setTask(Task task)
    {
        this.task = task;
        this.project = task.getParent();

        isDone.setSelected(task.isDone());
        selectStartDate.setValue(task.getStartDate());
        selectDueDate.setValue(task.getDueDate());
        selectRepeat.setValue(task.getRepeat());
        taskName.setText(task.getTitle());
        selectProject.setValue(project);
        Platform.runLater(() -> taskName.requestFocus());
        comments.setText(task.getComments());
        selectPriority.setValue(task.getPriority());
    }

    /**
     * Saves the task and exits.
     * Displays an popup alert window if the user wrote any illegal input.
     */
    @FXML
    protected void saveAndExit()
    {
        try
        {
            super.saveAndExit();
        }
        catch (IllegalArgumentException e)
        {
            SoundEffects.playErrorSound();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + e.getMessage());
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    /**
     * Deletes the task.
     */
    @FXML
    private void delete()
    {
        task.deleteItself();
        cancel();
    }

    @Override
    public String getMainMenuTitle()
    {
        return "Edit Task";
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof EditTaskView && ((EditTaskView) view).task.equals(task);
    }
}
