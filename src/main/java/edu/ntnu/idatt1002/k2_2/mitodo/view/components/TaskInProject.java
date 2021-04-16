package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.EditTaskView;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;

public class TaskInProject extends View
{
    @FXML
    BorderPane parent;
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
    boolean fromCalendar = false;


    Task task;
    Project project;

    public void initialize(){
        URL editButtonUrl = getClass().getResource("/images/editImage.png");
        URL deleteButtonUrl = getClass().getResource("/images/deleteImage.png");
        ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());
        ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
        setDeleteImage(deleteButton);
        setEditImage(editButton);
    }

    public void setTask(Task t){
        this.task = t;
        setInfo();
    }
    public void setTask(Task t, boolean calendarView){
        this.task = t;
        this.fromCalendar = calendarView;
        setInfo();
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
        image.setFitHeight(25);
        image.setFitWidth(25);
        editButton.setGraphic(image);
    }
    public void setDeleteImage(ImageView image){
        image.setFitHeight(25);
        image.setFitWidth(25);
        deleteButton.setGraphic(image);
    }
    public void handleTaskIsDoneButtonClick()
    {
        task.toggleIsDone();
        if(fromCalendar){
            Client.setView("CalendarView");
        }
        else{
            ProjectView projectView = (ProjectView) Client.setView("ProjectView"); //TODO denne koden gjør at en recurring task kommer, men er stuttery. Er det mulig å gjøre at når man checker task at den "går litt inn i skjermen" for å maskere? Får også følelsen av å faktisk trykke på noe
            projectView.setProject(project);
        }
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
        System.out.println(project.getTitle());
        project.removeTasksFromSubProjects(task.getID()); //REMOVES PROPERLY
        if(fromCalendar){
            Client.setView("CalendarView");
        }
        else{
            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(project);
        }
    }
    private void setInfo(){
        setPriorityText(task.getPriority().toString());
        setDate(task.getStartDateAsString(), task.getDueDateAsString());
        setIsDone(task.isDone());
        setProjectName(task.getProject().getTitle());
        setProject(task.getProject());
        setTaskName(task.getTitle()); //set label
    }
    public Node getParent() {
        return parent;
    }
}
