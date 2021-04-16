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

import javafx.scene.control.*;
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
    private ScrollPane scrollParent;
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

    boolean showingSubTasks;

    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Standard",
                        "Priority",
                        "Due Date",
                        "Start Date"
                );
        filterBox.setItems(options);

        ObservableList<String> showOptions =
                FXCollections.observableArrayList(
                        "Show Tasks",
                        "Show Sub Projects",
                        "Show Sub Project tasks"
                );
        projectsOrTasks.setItems(showOptions);
    }
    //TODO DET FUNGERE IKKJE MED OVERPROSJEKT FORDI DEN GÅR IKKJE GJENNOM UNDERPROSJEKTA FOR Å SORTERA FML
    public void sort(){
        String value = filterBox.getValue().toString();
        String oldText = projectsOrTasks.getPromptText();
        ArrayList<Task> sortedList;
        if (showingSubTasks){sortedList = project.getAllSubProjectTasks(); }
        else sortedList = project.getTasks();
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        projectView.setFilterBoxText(value, oldText, showingSubTasks);
        switch (value) {
            case "Standard":
                projectView.setProjectSorted(project, sortedList, showingSubTasks);
                break;
            case "Priority":
                project.sortTasksByPriority(sortedList);
                projectView.setProjectSorted(project, sortedList, showingSubTasks);
                break;
            case "Due Date":
                project.sortTasksByDueDate(sortedList);
                projectView.setProjectSorted(project, sortedList, showingSubTasks);
                break;
            case "Start Date":
                project.sortTasksByStartDate(sortedList);
                projectView.setProjectSorted(project, sortedList, showingSubTasks);
                break;
            default:
                System.out.println("Something went wrong with sorting of tasks");
                break;
        }
    }

    public void setProject(Project projectMain)
    {
        this.project = projectMain;
        showingSubTasks = false;
        if (project.getTitle().equals(Client.getQuickTasks().getTitle())){
            projectsOrTasks.setVisible(false);
            editButton.setVisible(false);
        }
        if(project.getProjects().size()<1){
            projectsOrTasks.setVisible(false);
        }
        addTasks();
        headline.setText(projectMain.getTitle());
    }
    public void setProjectSorted(Project projectMain, ArrayList<Task> sortedTask, boolean subTasks)
    {
        if (subTasks){showingSubTasks = true;}
        this.project = projectMain;
        if (project.getTitle().equals(Client.getQuickTasks().getTitle())){
            projectsOrTasks.setVisible(false);
            editButton.setVisible(false);
        }
        if(project.getProjects().size()<1){
            projectsOrTasks.setVisible(false);
        }
        addTasksSorted(sortedTask);
        headline.setText(projectMain.getTitle());
    }

    public void showProjectOrTask(){
        System.out.println(projectsOrTasks.getValue());
        String value = (String) projectsOrTasks.getValue();
        switch (value){
            case "Show Tasks":
                ProjectView projectViewWithOut = (ProjectView) Client.setView("ProjectView");
                projectViewWithOut.setProjectsOrTasksText(value);
                projectViewWithOut.setProjectWithOrWithoutProjects(project, false);
                break;
            case "Show Sub Projects":
                ProjectView projectViewWithSubs = (ProjectView) Client.setView("ProjectView");
                projectViewWithSubs.setProjectsOrTasksText(value);
                projectViewWithSubs.setProjectWithOrWithoutProjects(project, true);
                break;
            case "Show Sub Project tasks":
                ProjectView projectViewWithSubTasks = (ProjectView) Client.setView("ProjectView");
                projectViewWithSubTasks.setProjectsOrTasksText(value);
                projectViewWithSubTasks.setProjectSorted(project, project.getAllSubProjectTasks(), true);
                break;
            default:
                System.out.println("Something went wrong!");
                break;
        }
    }
    public void setProjectsOrTasksText(String s){
        projectsOrTasks.setPromptText(s);
    }
    public void setFilterBoxText(String s, String t,boolean b){
        filterBox.setPromptText(s);
        System.out.println(t);
        if(b){projectsOrTasks.setPromptText(t);}
    }
    public void setProjectWithOrWithoutProjects(Project projectMain, boolean showProjects)
    {
        this.project = projectMain;
        if (showProjects){
            if(project.getProjects().size()>0){
                addSubProjects();
                filterBox.setDisable(true);
            }
            else{
                addTasks();
            }
        }
        else addTasks();
        headline.setText(projectMain.getTitle());
        showingSubTasks = false;
    }

    @Override
    public Node getParent()
    {
        return scrollParent;
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
        if (project.getTasks().size() < 1){
            parent.getChildren().add(noTaskMessageLabel()); //parent.getChildren().add(hBox);
        }
        else {

            for (Task s : project.getTasks()) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    Node node = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(node);

                    TaskInProject controller = loader.getController();

                    controller.setProject(project);
                    controller.setTask(s);
                    setControllerInfo(controller,s);

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
                    BorderPane borderPane = loader.load(getClass().getResource("/fxml/TaskInProject.fxml").openStream());
                    parent.getChildren().add(borderPane);
                    TaskInProject controller = loader.getController();

                    controller.setProject(project);
                    controller.setTask(s);
                    setControllerInfo(controller,s);

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
                    TaskInProject controller = loader.getController();

                    controller.setProject(project);
                    controller.setTask(s);
                    setControllerInfo(controller,s);

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
        noTaskMessage.setPadding(new Insets(8,8,8,8));
        noTaskMessage.getStyleClass().add("h2");
        return noTaskMessage;
    }
    public void setControllerInfo(TaskInProject controller, Task s){
        URL editButtonUrl = getClass().getResource("/images/editImage.jpg");
        URL deleteButtonUrl = getClass().getResource("/images/deleteImage.jpg");
        ImageView deleteButton = new ImageView(deleteButtonUrl.toExternalForm());
        ImageView editButton = new ImageView(editButtonUrl.toExternalForm());

        controller.setTaskName(s.getTitle()); //set label
        controller.setPriorityText(s.getPriority().toString().toLowerCase());
        controller.setDate(s.getStartDateAsString(), s.getDueDateAsString());
        controller.setIsDone(s.isDone());
        controller.setEditImage(editButton);
        controller.setDeleteImage(deleteButton);

        if (showingSubTasks){controller.setProjectName(s.getProject().getTitle());}
        else controller.setProjectNameDisabled();
    }
  }
