package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class EditTaskView extends View
{
    @FXML
    public ChoiceBox<PriorityEnum> selectPriority;
    @FXML
    private  TextArea comments;
    @FXML
    private  DatePicker selectStartDate;
    @FXML
    private  DatePicker selectDueDate;
    @FXML
    private  TextField taskName;
    @FXML
    private VBox parent;

    private Task task;

    public EditTaskView() {
        this.task = new Task("Default");
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void update() {
        selectStartDate.setValue(task.getStartDate());
        selectDueDate.setValue(task.getDueDate());
        selectPriority.setValue(task.getPriority());
        taskName.setText(task.getTitle());
        comments.setText(task.getComments());
        selectPriority.getItems().clear();
        for (PriorityEnum priority : PriorityEnum.values()) {
            selectPriority.getItems().add(priority);
        }
        selectPriority.setValue(task.getPriority());
    }

    public void save() {
        task.setStartDate(selectStartDate.getValue());
        task.setDueDate(selectDueDate.getValue());
        task.setComments(comments.getText());
        task.setPriority(selectPriority.getValue());
        task.setTitle(taskName.getText());
        update();
    }

    public void exit() {

    }

    public void setStartDate() {

    }

    public void setDueDate() {
    }

    public void setPriority() {

    }

    public Node getParent()
    {
        return parent;
    }
}
