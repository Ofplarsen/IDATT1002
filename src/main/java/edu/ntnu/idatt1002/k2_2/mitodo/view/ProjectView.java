package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.EnumToStringConverter;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.TaskListSorter;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.DragAndDropManager;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.SubProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskInProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.EditProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.CreateTaskView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private ArrayList<UserProject> subprojects;

    public enum SortOption
    {
        STANDARD,
        IS_DONE,
        PRIORITY,
        DUE_DATE,
        START_DATE
    }

    public enum ShowOption
    {
        TASKS,
        SUBPROJECTS,
        ALL_TASKS
    }

    @FXML
    private void initialize()
    {
        sortByComboBox.setConverter(new EnumToStringConverter<>());
        sortByComboBox.setItems(FXCollections.observableArrayList(SortOption.values()));
        sortByComboBox.setValue(SortOption.STANDARD);

        showComboBox.setConverter(new EnumToStringConverter<>());
        showComboBox.setItems(FXCollections.observableArrayList(ShowOption.values()));
        showComboBox.setValue(ShowOption.TASKS);
    }

    public void setProject(Project project)
    {
        this.project = project;

        if (project instanceof RootProject)
        {
            setElementVisible(editProjectButton, false);
            setElementVisible(showContainer, false);
        }

        if (project.getProjects().size()==0)
        {
            setElementVisible(showContainer, false);
        }

        this.tasks = project.getTasks();
        this.subprojects = project.getProjects();
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
        this.title.setText(project.getTitle());
        Platform.runLater(() -> title.requestFocus());

        ShowOption showOption = showComboBox.getValue();
        switch (showOption)
        {
            case SUBPROJECTS:
                fillWithSubprojects();
                break;
            case ALL_TASKS:
                tasks = project.getAllTasks();
                updateSortOption();
                break;
            case TASKS:
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
            case IS_DONE:
                TaskListSorter.sortByIsDone(tasks);
                break;
            case PRIORITY:
                TaskListSorter.sortByPriority(tasks, increasing);
                break;
            case START_DATE:
                TaskListSorter.sortByStartDate(tasks, increasing);
                break;
            case DUE_DATE:
                TaskListSorter.sortByDueDate(tasks, increasing);
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
            SubProject subProject = (SubProject) Client.getComponent("SubProject");
            subProject.setProjectAndListContainer(project, listContainer);
            listContainer.getChildren().add(subProject.getParent());
        }
    }

    private void fillWithTasks()
    {
        setElementVisible(sortByContainer, true);
        listContainer.getChildren().clear();


        ArrayList<Task> expiredTasks = (ArrayList<Task>) tasks.stream().filter(Task::isExpired).collect(Collectors.toList());
        tasks.removeAll(expiredTasks);
        if(!expiredTasks.isEmpty()){
            addLabel("Overdue Tasks");
            addSeperator(0, true);
            for (Task task : expiredTasks){
                TaskInProject taskInProject = (TaskInProject) Client.getComponent("TaskInProject");
                taskInProject.setTask(task);
                taskInProject.setView(this);
                listContainer.getChildren().add(taskInProject.getParent());
                addSeperator(project.getTasks().indexOf(task) +1, true);
            }
            addLabel("Tasks");
        }

        addSeperator(0, false);
        for (Task task : tasks)
        {
            TaskInProject taskInProject = (TaskInProject) Client.getComponent("TaskInProject");
            taskInProject.setTask(task);
            taskInProject.setView(this);
            listContainer.getChildren().add(taskInProject.getParent());
            addSeperator(project.getTasks().indexOf(task) +1, false);
        }
    }

    private void addSeperator(int index, boolean expired)
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(20);
        borderPane.setMinHeight(20);

        if (showComboBox.getValue() == ShowOption.TASKS && sortByComboBox.getValue() == SortOption.STANDARD)
        borderPane.setOnDragOver(event ->
        {
            Object value = DragAndDropManager.getValue();
            if (value instanceof Task)
            {
                Task task = (Task) value;
                if (task.isExpired() == expired)
                {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            }
        });

        borderPane.setOnDragDropped(event ->
        {
            Object value = DragAndDropManager.getValue();
            if (value instanceof Task)
            {
                Task task = (Task) value;
                project.moveTask(task, index);
                update();
            }
        });


        listContainer.getChildren().add(borderPane);
    }

    private void setElementVisible(Node node, boolean visible)
    {
        node.setVisible(visible); //Trur at den disable den
        node.setManaged(visible);
        node.setDisable(!visible);
    }
    private void addLabel(String title)
    {
        Text todayLabel = new Text(title);
        todayLabel.setFont(new Font("System", 32));
        todayLabel.setId("header");
        listContainer.getChildren().add(todayLabel);
    }

    @Override
    public Node getParent()
    {
        return parent;
    }

    @Override
    public String getMainMenuTitle()
    {
        return project.getTitle();
    }

    @Override
    public boolean equals(View view)
    {
        return view instanceof ProjectView && ((ProjectView) view).project.equals(project);
    }
}
