package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.effects.SoundEffects;
import edu.ntnu.idatt1002.k2_2.mitodo.view.Component;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.EditTaskView;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;

public class TaskInProject extends Component
{
    @FXML
    private BorderPane parent;
    @FXML
    private CheckBox isDoneCheckBox;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label repeatLabel;
    @FXML
    private Label projectNameLabel;
    @FXML
    private Button deleteButton;

    private Task task;
    private View view;

    @FXML
    private void initialize()
    {
        setButtonImage(deleteButton, "deleteImage.png");
    }

    public void setTask(Task task)
    {
        this.task = task;
        setInfo();

        parent.setOnDragDetected(dragEvent ->
        {
            Dragboard db = parent.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cc = new ClipboardContent();
            cc.putString(task.getTitle());
            db.setContent(cc);
            DragAndDropManager.setValue(task);
        });
    }

    public void setView(View view)
    {
        this.view = view;
    }

    private void setButtonImage(Button button, String imageFileName)
    {
        URL deleteImageUrl = getClass().getResource("/images/" + imageFileName);
        ImageView image = new ImageView(deleteImageUrl.toExternalForm());
        image.setFitHeight(25);
        image.setFitWidth(25);
        button.setGraphic(image);
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
            case Undefined:
                priorityLabel.setText("");
                break;

            case Low:
                parent.getStyleClass().add("priority-low");
                priorityLabel.setText("P3");
                break;

            case Medium:
                parent.getStyleClass().add("priority-medium");
                priorityLabel.setText("P2");
                break;

            case High:
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

    private void setInfo()
    {
        isDoneCheckBox.setSelected(task.isDone());
        isDoneCheckBox.setText(task.getTitle());
        startDateLabel.setText(task.getStartDateAsString());
        dueDateLabel.setText(task.getDueDateAsString());
        repeatLabel.setText(task.getRepeat() == RepeatEnum.DoesNotRepeat? "" : task.getRepeat().toString());
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
