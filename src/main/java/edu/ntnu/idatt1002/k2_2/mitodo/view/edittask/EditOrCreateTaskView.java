package edu.ntnu.idatt1002.k2_2.mitodo.view.edittask;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.effects.SoundEffects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public abstract class EditOrCreateTaskView extends View
{
    @FXML
    protected VBox parent;
    @FXML
    protected CheckBox isDone;
    @FXML
    protected TextField taskName;
    @FXML
    protected TextArea comments;
    @FXML
    protected DatePicker selectStartDate;
    @FXML
    protected DatePicker selectDueDate;
    @FXML
    protected ChoiceBox<RepeatEnum> selectRepeat;
    @FXML
    protected ChoiceBox<PriorityEnum> selectPriority;

    protected Task task;
    protected Project project;

    @FXML
    public void initialize()
    {
        selectPriority.getItems().setAll(PriorityEnum.values());
        selectPriority.setValue(PriorityEnum.Undefined);

        selectRepeat.setConverter(RepeatEnum.toString);
        selectRepeat.getItems().setAll(RepeatEnum.values());
        selectRepeat.setValue(RepeatEnum.DoesNotRepeat);
    }

    @FXML
    protected void handleIsDoneCheckBox()
    {
        if(isDone.isSelected())
        {
            SoundEffects.playPlingSound();
        }
    }

    @FXML
    protected void clearDates()
    {
        selectDueDate.setValue(null);
        selectStartDate.setValue(null);
    }

    protected void saveAndExit()
    {
        try
        {
            task.setDone(isDone.isSelected());
            task.setDates(selectStartDate.getValue(),selectDueDate.getValue(), selectRepeat.getValue());
            task.setComments(comments.getText());
            task.setPriority(selectPriority.getValue());
            task.setTitle(taskName.getText());
            cancel();
        }
        catch (IllegalArgumentException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    protected void cancel()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
