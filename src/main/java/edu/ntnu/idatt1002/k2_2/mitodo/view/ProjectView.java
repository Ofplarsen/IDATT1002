package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.SubProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class ProjectView extends View
{
    private Project project;

    @FXML
    private VBox parent;
    @FXML
    private Label headline;
    @FXML
    private ComboBox filterBox;
    @FXML
    private Button editButton;
    @FXML
    private ComboBox projectsOrTasks;

    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Priority",
                        "Due Date",
                        "Start Date"
                );
        filterBox.setItems(options);

        ObservableList<String> showOptions =
                FXCollections.observableArrayList(
                        "Show All Tasks",
                        "Show All Sub Projects"
                );
        projectsOrTasks.setItems(showOptions);
    }
    //TODO DET FUNGERE IKKJE MED OVERPROSJEKT FORDI DEN GÅR IKKJE GJENNOM UNDERPROSJEKTA FOR Å SORTERA FML
    public void sort(){
        System.out.println(filterBox.getValue().toString());
        String value = filterBox.getValue().toString();
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        ArrayList<Task> sortedList = project.getAllTasks();
        switch (value){
            case "Priority":
                project.sortTasksByPriority(sortedList);
                projectView.setProjectSorted(project,sortedList);
                break;
            case "Due Date":
                project.sortTasksByDueDate(sortedList);
                projectView.setProjectSorted(project, sortedList);
                break;
            case "Start Date":
                project.sortTasksByStartDate(sortedList);
                projectView.setProjectSorted(project, sortedList);
                break;
            default:
                System.out.println("Something went wrong with sorting of tasks");
                break;
        }
        filterBox.setPromptText(value);
       //ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        //projectView.setProject(project);
    }

    public void setProject(Project projectMain)
    {
        this.project = projectMain;
        if (project.getTitle().equals(Client.getQuickTasks().getTitle())){
            projectsOrTasks.setDisable(true);
            projectsOrTasks.setVisible(false);
            editButton.setDisable(true);
            editButton.setVisible(false);
        }
        if(project.getProjects().size()<1){
            projectsOrTasks.setDisable(true);
            projectsOrTasks.setVisible(false);
        }
        addTasks();
        headline.setText(projectMain.getTitle());
    }
    public void setProjectSorted(Project projectMain, ArrayList<Task> sortedTask)
    {
        this.project = projectMain;
        if (project.getTitle().equals(Client.getQuickTasks().getTitle())){
            projectsOrTasks.setDisable(true);
            projectsOrTasks.setVisible(false);
            editButton.setDisable(true);
            editButton.setVisible(false);
            addTasks();
        }
        if(project.getProjects().size()<1){
            projectsOrTasks.setDisable(true);
            projectsOrTasks.setVisible(false);
        }
        addTasksSorted(sortedTask);
        headline.setText(projectMain.getTitle());
    }

    public void showProjectOrTask(){
        String value = projectsOrTasks.getValue().toString();
        switch (value){
            case "Show All Tasks":
                ProjectView projectViewWithOut = (ProjectView) Client.setView("ProjectView");
                projectViewWithOut.setProjectWithOrWithoutProjects(project, false);
                break;
            case "Show All Sub Projects":
                ProjectView projectViewWithSubs = (ProjectView) Client.setView("ProjectView");
                projectViewWithSubs.setProjectWithOrWithoutProjects(project, true);
                break;
            default:
                System.out.println("Something went wrong!");
                break;
        }
    }
    public void setProjectWithOrWithoutProjects(Project projectMain, boolean showProjects)
    {
        this.project = projectMain;
        if (showProjects){
            if(project.getProjects().size()>0){
                addSubProjects();
            }
            else{
                addTasks();
            }
        }
        else addTasks();
        headline.setText(projectMain.getTitle());
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

  public void handleAddTaskButton() {
        EditTaskView editTaskView = (EditTaskView) Client.setView("EditTaskView");
        editTaskView.setProject(project);
        editTaskView.update();
    }

    public void handleEditButtonClick(){
        EditProjectView editProjectView =(EditProjectView) Client.setView("EditProjectView");
        editProjectView.setProject(project);
    }
//TODO Maybe find a way to optimize this?
    public void addTasks(){
        if (project.getAllTasks().size() < 1){
            parent.getChildren().add(noTaskMessageLabel()); //parent.getChildren().add(hBox);
        }
        else {

            for (Task s : project.getAllTasks()) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    Node node = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(node);
                    URL editButtonUrl = getClass().getResource("/images/editImage.jpg");
                    URL deleteButtonUrl = getClass().getResource("/images/deleteImage.jpg");
                    ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
                    TaskInProject controller = loader.getController();
                    ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());

                    controller.setProject(project);
                    controller.setTask(s);
                    setControllerInfo(controller,s,editButton,deleteButton);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public void addTasksSorted(ArrayList<Task> sortedList){
        if (sortedList.size() < 1){
            parent.getChildren().add(noTaskMessageLabel());
        }
        else {

            for (Task s : sortedList) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    Node node = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(node);
                    URL editButtonUrl = getClass().getResource("/images/editImage.jpg");
                    URL deleteButtonUrl = getClass().getResource("/images/deleteImage.jpg");
                    ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
                    TaskInProject controller = loader.getController();
                    ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());

                    controller.setProject(project);
                    controller.setTask(s);
                    setControllerInfo(controller,s,editButton,deleteButton);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public void addSubProjects(){
        for (Project p: project.getProjects()) {
            FXMLLoader loader = new FXMLLoader();
            try {
                HBox hbox = loader.load(getClass().getResource("/fxml/SubProject.fxml").openStream());
                CheckBox checkBox = new CheckBox("Show Tasks");
                //checkBox.autosize();
                checkBox.setFont(new Font("System",22));
                checkBox.setPrefSize(270,120);
                checkBox.setOnAction(actionEvent -> {
                    int j = parent.getChildren().indexOf(hbox)+1;
                    handleShowTaskBoxActive(checkBox,p, j);
                });
                hbox.getChildren().add(checkBox);
                parent.getChildren().add(hbox);
                SubProject controller = loader.getController();
                controller.setInfo(p);

            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
    }
    public void addTasksForSub(Project subProject, int j){
        if (subProject.getAllTasks().size() < 1){
            parent.getChildren().add(j,noTaskMessageLabel()); //parent.getChildren().add(hBox);
        }
        else {

            for (Task s : subProject.getAllTasks()) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    Node node = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(j,node);
                    URL editButtonUrl = getClass().getResource("/images/editImage.jpg");
                    URL deleteButtonUrl = getClass().getResource("/images/deleteImage.jpg");
                    ImageView editButton = new ImageView(editButtonUrl.toExternalForm());
                    TaskInProject controller = loader.getController();
                    ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());

                    controller.setProject(project);
                    controller.setTask(s);
                    setControllerInfo(controller,s,editButton,deleteButton);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public void handleShowTaskBoxActive(CheckBox showTasksBox, Project p, int j) {
        if (showTasksBox.isSelected()) {
            addTasksForSub(p, j);

        } else {
            if (p.getAllTasks().size() == 0) {
                parent.getChildren().remove(j);
            } else {
                int size = p.getAllTasks().size() + 1;
                for (int i = 1; i < size; i++) {
                    parent.getChildren().remove(j);
                }
            }
        }
    }
    public Label noTaskMessageLabel(){
        Label noTaskMessage = new Label("No Tasks Currently Added");
        noTaskMessage.setPadding(new Insets(30,0,0,8));
        noTaskMessage.setFont(new Font(40));
        return noTaskMessage;
    }
    public void setControllerInfo(TaskInProject controller, Task s, ImageView editButton, ImageView deleteButton){
        controller.setTaskName(s.getTitle()); //set label
        controller.setPriorityText(s.getPriority().toString().toLowerCase());
        controller.setDate(s.getStartDateAsString(), s.getDueDateAsString());
        controller.setIsDone(s.isDone());
        controller.setEditImage(editButton);
        controller.setDeleteImage(deleteButton);
    }
  }
