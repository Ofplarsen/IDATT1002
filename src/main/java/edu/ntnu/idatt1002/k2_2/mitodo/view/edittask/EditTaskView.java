package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;

import edu.ntnu.idatt1002.k2_2.mitodo.effects.SoundEffects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.CalendarView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

public class EditTaskView extends EditOrCreateTaskView
{
    public void setTask(Task task)
    {
        this.task = task;
        this.project = task.getParent();

        isDone.setSelected(task.isDone());
        selectStartDate.setValue(task.getStartDate());
        selectDueDate.setValue(task.getDueDate());
        selectRepeat.setValue(task.getRepeat());
        taskName.setText(task.getTitle());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taskName.requestFocus();
            }
        });
        comments.setText(task.getComments());
        selectPriority.setValue(task.getPriority());
    }

    @FXML
    protected void saveAndExit()
    {
        try {
            super.saveAndExit();
        }catch (IllegalArgumentException e){
            SoundEffects.playErrorSound();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

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
