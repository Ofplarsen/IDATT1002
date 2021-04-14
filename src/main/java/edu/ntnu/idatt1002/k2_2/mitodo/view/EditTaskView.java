package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
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
    private ChoiceBox<PriorityEnum> selectPriority;
    @FXML
    private CheckBox checked;
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
    //TODO: This could be done much better, if you try to go back in a task that dosen't have a project, everything goes bad
    private Project project;

    public EditTaskView() {
        this.task = new Task("Default");
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
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
        try {
            task.setStartDate(selectStartDate.getValue());
            task.setDueDate(selectDueDate.getValue());
            task.setComments(comments.getText());
            task.setPriority(selectPriority.getValue());
            task.setTitle(taskName.getText());
            update();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void exit() {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
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
