package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
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

    public void setView(View view)
    {
        this.view = view;
    }

    @FXML
    public void boxKeyListener(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
            editTaskView.setTask(task);
        }
    }

    private void setPriorityInfo()
    {
        if (parent.getStyleClass().size() > 1)
        {
            parent.getStyleClass().remove(1);
        }

        switch (task.getPriority()) {
            case UNDEFINED:
                priorityLabel.setText("");
                break;

            case LOW:
                parent.getStyleClass().add("priority-low");
                priorityLabel.setText("P3");
                break;

            case MEDIUM:
                parent.getStyleClass().add("priority-medium");
                priorityLabel.setText("P2");
                break;

            case HIGH:
                parent.getStyleClass().add("priority-high");
                priorityLabel.setText("P1");
                break;
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
    public void removeProjectLabel(){
        projectNameLabel.setVisible(false);
    }

    public void updateDateThings() {
        if (task.getDueDate() != null && task.getStartDate() == null) {
            dateLabel.setText("Due: " + task.getDueDateAsString());
        }
        if (task.getDueDate() == null && task.getStartDate() != null) {
            dateLabel.setText("Starting: " + task.getStartDateAsString());
        }
        if (task.getDueDate() != null && task.getStartDate() != null) {
            dateLabel.setText(task.getStartDateAsString() + " - " + task.getDueDateAsString());
        }
    }

    private void setInfo()
    {
        updateDateThings();
        isDoneCheckBox.setSelected(task.isDone());
        isDoneCheckBox.setText(task.getTitle());
        repeatLabel.setText(task.getRepeat() == RepeatEnum.DOES_NOT_REPEAT ? "" : task.getRepeat().toString());
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
