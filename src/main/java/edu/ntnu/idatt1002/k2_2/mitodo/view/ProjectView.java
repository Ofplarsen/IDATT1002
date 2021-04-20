package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.TaskListSorter;
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
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ProjectView extends View
{
    @FXML
    private VBox parent;
    @FXML
    private Text title;
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
    private ArrayList<UserProject> subprojects;

    public enum SortOption
    {
        IsDone,
        PriorityIncreasing,
        PriorityDecreasing,
        DueDateIncreasing,
        DueDateDecreasing,
        StartDateIncreasing,
        StartDateDecreasing
    }

    public enum ShowOption
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

        if (project instanceof RootProject)
        {
            setElementVisible(editProjectButton, false);
            setElementVisible(showContainer, false);
        }

        this.tasks = project.getTasks();

        this.subprojects = project.getProjects();

        this.title.setText(project.getTitle());
        Platform.runLater(() -> title.requestFocus());
        update();
    }

    @FXML
    public void setShowOption(ShowOption option)
    {
        showComboBox.setValue(option);
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
                tasks = project.getAllTasks();
                updateSortOption();
                break;
            case Task:
                tasks = project.getTasks();
                updateSortOption();
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
        editProjectView.setProject((UserProject) project);
    }

    private boolean increasing = true;
    @FXML
    private void updateSortOption()
    {
        SortOption sortOption = sortByComboBox.getValue();
        switch (sortOption)
        {
            case IsDone:
                TaskListSorter.sortByIsDone(tasks);
                break;
            case PriorityIncreasing:
                TaskListSorter.sortByPriority(tasks, increasing);
                break;
            case PriorityDecreasing:
                TaskListSorter.sortByPriority(tasks, !increasing);
                break;
            case StartDateIncreasing:
                TaskListSorter.sortByStartDate(tasks, increasing);
                break;
            case StartDateDecreasing:
                TaskListSorter.sortByStartDate(tasks, !increasing);
                break;
            case DueDateIncreasing:
                TaskListSorter.sortByDueDate(tasks, increasing);
                break;
            case DueDateDecreasing:
                TaskListSorter.sortByDueDate(tasks, !increasing);
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
