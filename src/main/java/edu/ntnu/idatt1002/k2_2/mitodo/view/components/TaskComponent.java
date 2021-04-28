package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.EnumToStringConverter;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.util.SoundEffects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.Component;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.EditTaskView;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * Class representing a task component displayed in CalendarView and ProjectView when
 * the show option is set to tasks or all tasks.
 * Linked with the Task.fxml file.
 */
public class TaskComponent extends Component
{
    @FXML
    public Label dateLabel;
    @FXML
    private BorderPane parent;
    @FXML
    private CheckBox isDoneCheckBox;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label repeatLabel;
    @FXML
    private Label projectNameLabel;

    private Task task;
    private View view;

    /**
     * Sets the task this task component displays and links to.
     * @param task The task.
     */
    public void setTask(Task task)
    {
        this.task = task;
        setInfo();

        parent.setOnDragDetected(dragEvent ->
        {
            Dragboard dragboard = parent.startDragAndDrop(TransferMode.MOVE);

            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image image = parent.snapshot(parameters, null);
            dragboard.setDragView(image);

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString("");
            dragboard.setContent(clipboardContent);

            DragAndDropManager.setValue(task);

            dragEvent.consume();
        });
    }

    /**
     * Sets the view this task component is displayed in.
     * @param view The view.
     */
    public void setView(View view)
    {
        this.view = view;
    }

    /**
     * Key listener for the task component. Goes to edit task view if the key is enter.
     * @param keyEvent The key event.
     */
    @FXML
    private void boxKeyListener(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
            editTaskView.setTask(task);
        }
    }

    /**
     * Sets the priority text in the task component to match the task.
     */
    private void setPriorityInfo()
    {
        if (parent.getStyleClass().size() > 1)
        {
            parent.getStyleClass().remove(1);
        }

        PriorityEnum priority = task.getPriority();
        String priorityString = new EnumToStringConverter<PriorityEnum>().toString(priority);

        parent.getStyleClass().add("priority-" + priorityString.toLowerCase());

        if (priority == PriorityEnum.UNDEFINED)
        {
            priorityLabel.setText("");
        }
        else
        {
            priorityLabel.setText(priorityString + " priority");
        }
    }

    @FXML
    private void handleTaskIsDoneButtonClick()
    {
        task.toggleIsDone();
        if(task.isDone())
        {
            SoundEffects.playPlingSound();
        }
        view.update();
    }

    @FXML
    private void handleEditTaskButtonClick()
    {
        EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
        editTaskView.setTask(task);
    }

    @FXML
    private void handleDeleteTaskButtonClick()
    {
        task.deleteItself();
        view.update();
    }

    /**
     * Removes the project name label from the task component.
     */
    public void removeProjectLabel()
    {
        projectNameLabel.setText("");
    }

    /**
     * Sets the date label in the task component to match the task dates.
     */
    public void setDateLabel()
    {
        if (task.getDueDate() != null && task.getStartDate() == null)
        {
            dateLabel.setText("Due: " + task.getDueDateAsString());
        }
        if (task.getDueDate() == null && task.getStartDate() != null)
        {
            dateLabel.setText("Starting: " + task.getStartDateAsString());
        }
        if (task.getDueDate() != null && task.getStartDate() != null)
        {
            dateLabel.setText(task.getStartDateAsString() + " - " + task.getDueDateAsString());
        }
    }

    /**
     * Sets the info in the task component to match the task info.
     */
    private void setInfo()
    {
        setDateLabel();
        isDoneCheckBox.setSelected(task.isDone());
        isDoneCheckBox.setText(task.getTitle());
        repeatLabel.setText(task.getRepeat() == RepeatEnum.DOES_NOT_REPEAT ? "" : new EnumToStringConverter<RepeatEnum>().toString(task.getRepeat()));
        projectNameLabel.setText(task.getParent().getTitle());
        setPriorityInfo();
    }

    public Task getTask()
    {
        return task;
    }

    public Node getParent()
    {
        return parent;
    }
}
