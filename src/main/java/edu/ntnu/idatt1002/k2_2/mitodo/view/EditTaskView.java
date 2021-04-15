package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


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

    private Task task = null;
    //TODO: This could be done much better, if you try to go back in a task that dosen't have a project, everything goes bad
    private Project project;

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

    public void update()
    {
        selectPriority.getItems().setAll(PriorityEnum.values());

        if (task != null)
        {
            checked.setSelected(task.isDone());
            selectStartDate.setValue(task.getStartDate());
            selectDueDate.setValue(task.getDueDate());
            selectPriority.setValue(task.getPriority());
            taskName.setText(task.getTitle());
            comments.setText(task.getComments());
            selectPriority.setValue(task.getPriority());
        }
        else
        {
            taskName.setText("My task");
        }
    }

    public void saveAndExit() {
        if (task == null)
        {
            task = project.addTask(taskName.getText());
        }

        try {
            task.setDone(checked.isSelected());
            task.setStartDate(selectStartDate.getValue());
            task.setDueDate(selectDueDate.getValue());
            task.setDates(selectStartDate.getValue(),selectDueDate.getValue());
            task.setComments(comments.getText());
            task.setPriority(selectPriority.getValue());
            task.setTitle(taskName.getText());
            update();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        cancel();
    }

    public void cancel() {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }

    public void delete()
    {
        if (task != null)
        {
            project.removeTasksFromSubProjects(task.getID());
        }
        cancel();
    }

    public void clearDates(){
        selectDueDate.setValue(null);
        selectStartDate.setValue(null);
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
