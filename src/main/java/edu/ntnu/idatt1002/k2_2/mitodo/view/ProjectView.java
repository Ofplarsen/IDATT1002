package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.data.TaskListSorter;
import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.SubProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.EditProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.CreateTaskView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ProjectView extends View
{
    @FXML
    private VBox parent;
    @FXML
    private Label title;
    @FXML
    private Button editProjectButton;
    @FXML
    private HBox sortByContainer;
    @FXML
    private ComboBox<SortOption> sortByComboBox;
    @FXML
    private HBox showContainer;
    @FXML
    private ComboBox<ShowOption> showComboBox;
    @FXML
    private VBox listContainer;

    private Project project;
    private ArrayList<Task> tasks;
    private ArrayList<Project> subprojects;
    private boolean showingSubProjectTasks; //gjer sånn at når du komme tilbake frå edita task, så komme du tilbake til den orignale plassen

    private enum SortOption
    {
        IsDone,
        Priority,
        DueDate,
        StartDate
    }

    private enum ShowOption
    {
        Task,
        Subprojects,
        SubprojectsTasks
    }

    @FXML
    private void initialize()
    {
        sortByComboBox.setItems(FXCollections.observableArrayList(SortOption.values()));
        sortByComboBox.setValue(SortOption.IsDone);
        showComboBox.setItems(FXCollections.observableArrayList(ShowOption.values()));
        showComboBox.setValue(ShowOption.Task);
    }

    public void setProject(Project project)
    {
        this.project = project;

        if (project.equals(Client.getQuickTasks()))
        {
            setElementVisible(editProjectButton, false);
        }

        this.tasks = project.getTasks();

        this.subprojects = project.getProjects();

        if (subprojects.size() == 0)
        {
            setElementVisible(showContainer, false);
        }

        this.title.setText(project.getTitle());
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                title.requestFocus();
            }
        });
        update();
    }

    //Denna gjer sånn at når du komme tilbake frå editTask så får du det valget du hadde sist
    @FXML
    public void setShowOption(int i){
        switch (i){
            case 1:
               showComboBox.setValue(ShowOption.Task);
               break;
            case 2:
                showComboBox.setValue(ShowOption.Subprojects);
                break;
            case 3:
                showComboBox.setValue(ShowOption.SubprojectsTasks);
                break;
            default:
                break;
        }
        update();
    }

    @FXML
    private void updateShowOption()
    {
        update();
    }

    @Override
    public void update()
    {
        ShowOption showOption = showComboBox.getValue();
        switch (showOption)
        {
            case Subprojects:
                fillWithSubprojects();
                break;
            case SubprojectsTasks:
                tasks = project.getAllSubProjectTasks();
                updateSortOption();
                showingSubProjectTasks = true;
                break;
            case Task:
                tasks = project.getTasks();
                updateSortOption();
                showingSubProjectTasks= false;
                break;
        }
    }

    @FXML
    private void handleAddTaskButton()
    {
        CreateTaskView createTaskView = (CreateTaskView) Client.setView("CreateTaskView");
        createTaskView.setProject(project);
    }

    @FXML
    private void handleEditButtonClick()
    {
        EditProjectView editProjectView =(EditProjectView) Client.setView("EditProjectView");
        editProjectView.setProject(project);
    }

    @FXML
    private void updateSortOption()
    {
        SortOption sortOption = sortByComboBox.getValue();
        switch (sortOption)
        {
            case IsDone:
                TaskListSorter.sortByIsDone(tasks);
                break;
            case Priority:
                TaskListSorter.sortByPriority(tasks);
                break;
            case StartDate:
                TaskListSorter.sortByStartDate(tasks);
                break;
            case DueDate:
                TaskListSorter.sortByDueDate(tasks);
                break;
        }
        fillWithTasks();
    }

    private void fillWithSubprojects()
    {
        setElementVisible(sortByContainer, false);
        listContainer.getChildren().clear();

        for (Project project : subprojects)
        {
            SubProject subProject = (SubProject) FileManager.getView("SubProject");
            subProject.setProjectAndListContainer(project, listContainer);
            subProject.setOriginProject(this.project);
            listContainer.getChildren().add(subProject.getParent());
        }
    }

    private void fillWithTasks()
    {
        setElementVisible(sortByContainer, true);
        listContainer.getChildren().clear();

        for (Task task : tasks)
        {
            TaskInProject taskInProject = (TaskInProject) FileManager.getView("TaskInProject");
            taskInProject.setTask(task);
            if(showingSubProjectTasks){taskInProject.setProject(project,3);} //viss showing subtasks så endre han prosjektet til tasken til her (sånn at du ende opp på samme plass du begynte)
            taskInProject.setView(this);
            listContainer.getChildren().add(taskInProject.getParent());
        }
    }

    private void setElementVisible(Node node, boolean visible)
    {
        node.setVisible(visible);
        node.setManaged(visible);
        node.setDisable(!visible);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }
}
