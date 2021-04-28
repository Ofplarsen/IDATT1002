package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.EnumToStringConverter;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.TaskListSorter;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.DragAndDropManager;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.SubProjectComponent;
import edu.ntnu.idatt1002.k2_2.mitodo.view.components.TaskComponent;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.CreateProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.EditProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.CreateTaskView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
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
    private CheckBox ascendingCheckBox;
    @FXML
    private HBox showContainer;
    @FXML
    private ComboBox<ShowOption> showComboBox;

    @FXML
    private VBox expiredContainer;
    @FXML
    private VBox normalContainer;
    @FXML
    private VBox doneContainer;

    @FXML
    private Text overdueTitle;
    @FXML
    private Text normalTitle;
    @FXML
    private HBox doneTitleContainer;
    @FXML
    private CheckBox showDoneTasksCheckBox;

    private Project project;
    private ArrayList<UserProject> subprojects;

    private ArrayList<Task> tasks;
    private ArrayList<Task> overdueTasks;
    private ArrayList<Task> doneTasks;

    private final EventHandler<DragEvent> onOverdueTaskDragOverEventHandler = dragEvent ->
    {
        if (!isDragAndDrop()) return;

        Object obj = DragAndDropManager.getValue();
        if (!(obj instanceof Task)) return;

        Task task = (Task) obj;
        if (task.isOverdue() && !task.isDone())
        {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
    };

    private final EventHandler<DragEvent> onNormalTaskDragOverEventHandler = dragEvent ->
    {
        if (!isDragAndDrop()) return;

        Object obj = DragAndDropManager.getValue();
        if (!(obj instanceof Task)) return;

        Task task = (Task) obj;
        if (!task.isOverdue() && !task.isDone())
        {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
    };

    private final EventHandler<DragEvent> onDoneTaskDragOverEventHandler = dragEvent ->
    {
        if (!isDragAndDrop()) return;

        Object obj = DragAndDropManager.getValue();
        if (!(obj instanceof Task)) return;

        Task task = (Task) obj;
        if (task.isDone())
        {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
    };

    private final EventHandler<DragEvent> onSubprojectDragOverEventHandler = dragEvent ->
    {
        if (!isDragAndDrop()) return;

        Object obj = DragAndDropManager.getValue();
        if (obj instanceof UserProject)
        {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
    };

    public enum SortOption
    {
        STANDARD,
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
        this.tasks = project.getTasks();
        this.subprojects = project.getProjects();
        update();
    }

    @FXML
    private void handleAddTaskButton()
    {
        if (showComboBox.getValue() == ShowOption.SUBPROJECTS)
        {
            CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
            createProjectView.setParentProject(project);
        }
        else
        {
            CreateTaskView createTaskView = (CreateTaskView) Client.setView("CreateTaskView");
            createTaskView.setProject(project);
        }
    }

    @FXML
    private void handleEditButtonClick()
    {
        EditProjectView editProjectView =(EditProjectView) Client.setView("EditProjectView");
        editProjectView.setProject((UserProject) project);
    }

    @Override
    public void update()
    {
        this.title.setText(project.getTitle());
        Platform.runLater(() -> title.requestFocus());

        if (project.getProjects().size()==0)
        {
            setElementVisible(showContainer, false);
            showComboBox.setValue(ShowOption.TASKS);
            setElementVisible(sortByContainer, true);
        }
        else
        {
            setElementVisible(showContainer, true);
        }

        if (project instanceof RootProject)
        {
            setElementVisible(editProjectButton, false);
            setElementVisible(showContainer, false);
        }

        updateShowAndSortOption();
    }

    @FXML
    private void updateShowAndSortOption()
    {
        updateShowOption();
        updateSortOption();
        fillListContainer();
        updateDoneTaskContainer();
    }

    @FXML
    private void deleteDoneTasks(){
        for (Task e : doneTasks){
            project.removeTasksFromAll(e.getID());
        }
        update();
    }

    private void updateShowOption()
    {
        ShowOption showOption = showComboBox.getValue();
        switch (showOption)
        {
            case SUBPROJECTS:
                subprojects = project.getProjects();
                break;
            case ALL_TASKS:
                tasks = project.getAllTasks();
                break;
            case TASKS:
                tasks = project.getTasks();
                break;
        }
    }

    private void updateSortOption()
    {
        setElementVisible(ascendingCheckBox, true);
        boolean ascending = ascendingCheckBox.isSelected();

        SortOption sortOption = sortByComboBox.getValue();
        switch (sortOption)
        {
            case STANDARD:
                ascendingCheckBox.setDisable(true);
                break;
            case PRIORITY:
                TaskListSorter.sortByPriority(tasks, ascending);
                break;
            case START_DATE:
                TaskListSorter.sortByStartDate(tasks, ascending);
                break;
            case DUE_DATE:
                TaskListSorter.sortByDueDate(tasks, ascending);
                break;
        }

        overdueTasks = (ArrayList<Task>) tasks.stream().filter(Task::isOverdue).collect(Collectors.toList());
        tasks.removeAll(overdueTasks);

        doneTasks = (ArrayList<Task>) tasks.stream().filter(Task::isDone).collect(Collectors.toList());
        tasks.removeAll(doneTasks);
    }

    private void fillListContainer()
    {
        expiredContainer.getChildren().clear();
        normalContainer.getChildren().clear();
        doneContainer.getChildren().clear();

        setElementVisible(overdueTitle, false);
        setElementVisible(normalTitle, false);
        setElementVisible(doneTitleContainer, false);

        if (showComboBox.getValue() == ShowOption.SUBPROJECTS)
        {
            fillWithSubprojects();
        }
        else
        {
            fillWithTasks();
        }
    }

    private void fillWithSubprojects()
    {
        setElementVisible(sortByContainer, false);

        addSeparator(normalContainer, onSubprojectDragOverEventHandler, event -> onSubprojectDragDropped(0));
        for (Project subproject : subprojects)
        {
            SubProjectComponent subProjectComponent = (SubProjectComponent) Client.getComponent("SubProject");
            subProjectComponent.setProject(subproject);
            normalContainer.getChildren().add(subProjectComponent.getParent());
            int index = subprojects.indexOf(subproject) + 1;
            addSeparator(normalContainer, onSubprojectDragOverEventHandler, event -> onSubprojectDragDropped(index));
        }
    }

    private void fillWithTasks()
    {
        setElementVisible(sortByContainer, true);

        addExpiredTasks();
        addNormalTasks();
        addDoneTasks();
    }

    private void addExpiredTasks()
    {
        if(!overdueTasks.isEmpty())
        {
            setElementVisible(overdueTitle, true);

            if (!tasks.isEmpty())
            {
                setElementVisible(normalTitle, true);
            }

            addSeparator(expiredContainer, onOverdueTaskDragOverEventHandler, event -> onTaskDragDropped(0));
            for (Task task : overdueTasks)
            {
                addTask(expiredContainer, task);
                int index = project.getTasks().indexOf(task) + 1;
                addSeparator(expiredContainer, onOverdueTaskDragOverEventHandler, event -> onTaskDragDropped(index));
            }
        }
    }

    private void addNormalTasks()
    {
        if(tasks.isEmpty()){
            Label noTasksLabel = new Label("No Tasks");
            noTasksLabel.setFont(new Font("System", 32));
            normalContainer.getChildren().add(noTasksLabel);
        }
        else{
            addSeparator(normalContainer, onNormalTaskDragOverEventHandler, event -> onTaskDragDropped(0));
            for (Task task : tasks)
            {
                addTask(normalContainer, task);
                int index = project.getTasks().indexOf(task) + 1;
                addSeparator(normalContainer, onNormalTaskDragOverEventHandler, event -> onTaskDragDropped(index));
            }
        }
    }

    private void addDoneTasks()
    {
        if(!doneTasks.isEmpty())
        {
            setElementVisible(doneTitleContainer, true);

            addSeparator(doneContainer, onDoneTaskDragOverEventHandler, event -> onTaskDragDropped(0));
            for (Task task : doneTasks)
            {
                addTask(doneContainer, task);
                int index = project.getTasks().indexOf(task) + 1;
                addSeparator(doneContainer, onDoneTaskDragOverEventHandler, event -> onTaskDragDropped(index));
            }
        }
    }

    @FXML
    private void updateDoneTaskContainer()
    {
        setElementVisible(doneContainer, showDoneTasksCheckBox.isSelected());
    }

    private void addTask(VBox container, Task task)
    {
        TaskComponent taskComponent = (TaskComponent) Client.getComponent("Task");
        taskComponent.setTask(task);
        taskComponent.setView(this);

        if(showComboBox.getValue() == ShowOption.TASKS)
        {
            taskComponent.removeProjectLabel();
        }

        container.getChildren().add(taskComponent.getParent());
    }

    private void onTaskDragDropped(int index)
    {
        Task task = (Task) DragAndDropManager.getValue();
        project.moveTask(task, index);
        updateShowAndSortOption();
    }

    private void onSubprojectDragDropped(int index)
    {
        UserProject subproject = (UserProject) DragAndDropManager.getValue();
        project.moveProject(subproject, index);
        updateShowAndSortOption();
        Client.updateMainMenu();
    }

    private boolean isDragAndDrop()
    {
        if (showComboBox.getValue() == ShowOption.TASKS && sortByComboBox.getValue() == SortOption.STANDARD)
        {
            return true;
        }

        return  (showComboBox.getValue() == ShowOption.SUBPROJECTS);
    }

    private BorderPane addSeparator(VBox container)
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(20);
        borderPane.setMinHeight(20);
        container.getChildren().add(borderPane);
        return borderPane;
    }

    private void addSeparator(VBox container, EventHandler<DragEvent> onDragOverEventHandler, EventHandler<DragEvent> onDragDroppedEventHandler)
    {
        BorderPane borderPane = addSeparator(container);
        borderPane.setOnDragOver(onDragOverEventHandler);
        borderPane.setOnDragDropped(onDragDroppedEventHandler);
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
