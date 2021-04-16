package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.view.EditTaskView;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TaskInProject extends View
{
    @FXML
    HBox parent;
    @FXML
    CheckBox taskBox;
    @FXML
    Label priority;
    @FXML
    Label startDate;
    @FXML
    Label dueDate;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Label projectName;


    Task task;
    Project project;

    public void setTask(Task t){
        this.task = t;
    }

    public void setProject(Project projectMain)
    {
        this.project = projectMain;
    }

    public void setTaskName(String task){
        taskBox.setText(task);
    }
    public void setPriorityText(String prio){
        priority.setText(prio + " priority" );
    }
    public void setDate(String start, String end){
        startDate.setText(start);
        dueDate.setText(end);
    }
    public void setIsDone(boolean isDone)
    {
        taskBox.setSelected(isDone);
    }
    public void setEditImage(ImageView image){
        image.setFitHeight(40);
        image.setFitWidth(40);
        editButton.setGraphic(image);
    }
    public void setDeleteImage(ImageView image){
        image.setFitHeight(40);
        image.setFitWidth(40);
        deleteButton.setGraphic(image);
    }
    public void handleTaskIsDoneButtonClick()
    {
        task.toggleIsDone();
    }
    public void setProjectName(String project){
        projectName.setText(project);
    }
    public void setProjectNameDisabled(){
        projectName.setDisable(true);
        projectName.setVisible(false);
    }
    public void handleEditTaskButtonClick()
    {
        EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
        editTaskView.setTask(task);
        editTaskView.setProject(project);
        editTaskView.update();
    }

//TODO Idk viss denna egentlig fungere, e litt rar
    public void handleDeleteTaskButtonClick() {
        //project.removeTask(task.getID()); //DOES NOT REMOVE PROPERLY
        project.removeTasksFromSubProjects(task.getID()); //REMOVES PROPERLY
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setProject(project);
    }
    public Node getParent() {
        return parent;
    }
}
