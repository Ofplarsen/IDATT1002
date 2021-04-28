package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;
import edu.ntnu.idatt1002.k2_2.mitodo.view.*;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.CreateProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.EditProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.edittask.CreateTaskView;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;

/**
 * The main menu has a treeView that it fills with TreeItems containing MainMenuItems containing a View.
 * Keypress:
 * ENTER = Clicks on selected item/label
 * RIGHT = Folds out a folded tree branch
 * LEFT = Folds tree branch
 * UP = Selects label above
 * DOWN = Selects label below
 */
public class MainMenu
{
    private final TreeView treeView;
    private TreeItem<MainMenuItem> selectedTreeItem;
    private final ArrayList<TreeItem<MainMenuItem>> treeItems = new ArrayList<>();

    /**
     * Constructs a new main menu object with a TreeView.
     * @param treeView
     */
    public MainMenu(TreeView<MainMenuItem> treeView)
    {
        this.treeView = treeView;
    }

    /**
     * Highlights the current view in the main menu.
     * @param currentView The current view.
     */
    public void selectCurrentView(View currentView)
    {
        for (TreeItem<MainMenuItem> treeItem : treeItems)
        {
            if (treeItem.getValue().getView().equals(currentView))
            {
                selectedTreeItem = treeItem;
            }
        }
        treeView.getSelectionModel().select(selectedTreeItem);
    }

    /**
     * Gets all projects from Client and updates the content of the main menu.
     */
    public void update(View currentView)
    {
        treeItems.clear();

        TreeItem root = new TreeItem<>();
        root.setExpanded(true);

        ProjectView quickTasksView = (ProjectView) Client.getComponent("ProjectView");
        quickTasksView.setProject(Client.getRootProject());

        selectedTreeItem = makeTreeItem(root, quickTasksView, null);
        selectedTreeItem.getValue().setOnDragOver(event -> onDragOverProjectView(Client.getRootProject(), event));
        selectedTreeItem.getValue().setOnDragDropped(event -> onDragDroppedOnProjectView(Client.getRootProject()));

        makeTreeItem(root, (View) Client.getComponent("CalendarView"), null);

        makeTreeItem(root, (View) Client.getComponent("SettingsView"), null);

        CreateProjectView createProjectView = (CreateProjectView) Client.getComponent("CreateProjectView");
        createProjectView.setParentProject(Client.getRootProject());
        makeTreeItem(root, createProjectView, null);

        Separator separator = new Separator();
        TreeItem<Separator> separatorTreeItem = new TreeItem<>(separator);
        root.getChildren().add(separatorTreeItem);

        separator.setPrefHeight(20);
        separator.setOnDragOver(event ->onDragOverProjectView(Client.getRootProject(), event));
        separator.setOnDragDropped(event -> onDragDroppedOnProjectView(Client.getRootProject()));

        ArrayList<UserProject> projects = Client.getRootProject().getProjects();
        projects.forEach(project -> makeAllProjectTreeItems(root, project));

        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.setOnMouseClicked(this::onMainMenuItemEvent);
        treeView.setOnKeyPressed(this::onMainMenuItemEvent);

        selectCurrentView(currentView);
    }

    /**
     * Makes a TreeItem for a project with a context menu.
     * @param parent The parent TreeItem to create the TreeItem from.
     * @param project The project to create a TreeItem for.
     */
    private TreeItem<MainMenuItem> makeProjectTreeItem(TreeItem<MainMenuItem> parent, Project project)
    {
        ProjectView projectView = (ProjectView) Client.getComponent("ProjectView");
        projectView.setProject(project);

        MenuItem editProject = new MenuItem("Edit project");
        editProject.setOnAction(event ->
        {
            EditProjectView editProjectView = (EditProjectView) Client.setView("EditProjectView");
            editProjectView.setProject((UserProject) project);
        });

        MenuItem addSubProject = new MenuItem("Add subproject");
        addSubProject.setOnAction(event ->
        {
            CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
            createProjectView.setParentProject(project);
        });

        MenuItem addTask = new MenuItem("Add task");
        addTask.setOnAction(event ->
        {
            CreateTaskView createTaskView = (CreateTaskView) Client.setView("CreateTaskView");
            createTaskView.setProject(project);
        });
        MenuItem deleteProject = new MenuItem("Delete project");
        deleteProject.setOnAction(event ->
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the project?", ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait().ifPresent(type ->
            {
                if(type == ButtonType.OK)
                {
                    Client.getRootProject().removeProjectFromAll(project.getID());
                    if (project instanceof UserProject) {
                        ProjectView _projectView = (ProjectView) Client.setView("ProjectView");
                        UserProject currentProject = (UserProject) project;
                        _projectView.setProject(currentProject.getParent());
                    } else {
                        Client.setView("ProjectView");
                    }
                    Client.updateMainMenu();
                }
            });
        });


        ContextMenu contextMenu = new ContextMenu(editProject, addSubProject, addTask, deleteProject);

        TreeItem<MainMenuItem> projectItem = makeTreeItem(parent, projectView, contextMenu);

        Label label = projectItem.getValue().getLabel();

        label.setOnDragDetected(dragEvent ->
        {
            Dragboard dragboard = label.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString("");
            dragboard.setContent(clipboardContent);

            DragAndDropManager.setValue(project);

            dragEvent.consume();
        });
        projectItem.getValue().setOnDragOver(event -> onDragOverProjectView(project, event));
        projectItem.getValue().setOnDragDropped(event -> onDragDroppedOnProjectView(project));

        return projectItem;
    }

    /**
     * Makes a TreeItem for the project and all projects below the project.
     * @param parent The parent TreeItem to create the TreeItem from.
     * @param project The project to create TreeItems for.
     */
    private void makeAllProjectTreeItems(TreeItem<MainMenuItem> parent, Project project)
    {
        TreeItem<MainMenuItem> projectItem = makeProjectTreeItem(parent, project);
        project.getProjects().forEach(subProject -> makeAllProjectTreeItems(projectItem, subProject));
    }

    /**
     * Method for handling an object getting dragged over a TreeItem with a link to a ProjectView.
     * It accepts all tasks and user projects that can move to the project.
     * @param project The project it gets dragged over.
     * @param event The drag event.
     */
    private void onDragOverProjectView(Project project, DragEvent event)
    {
        Object obj = DragAndDropManager.getValue();
        if (obj instanceof Task)
        {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        else if (obj instanceof UserProject)
        {
            UserProject userProject = (UserProject) obj;
            if (userProject.canMoveTo(project))
            {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }

        Client.getCurrentView().update();
    }

    /**
     * Method for handling an object getting dropped on a TreeItem with a link to a ProjectView.
     * It adds the object to the project if it is a task or an user project that can move to the project.
     * @param project The project it gets dropped on.
     */
    private void onDragDroppedOnProjectView(Project project)
    {
        Object obj = DragAndDropManager.getValue();
        if (obj instanceof Task)
        {
            Task task = (Task) obj;
            task.getParent().moveTaskTo(task, project);
            task.deleteItself();
        }
        else if (obj instanceof UserProject)
        {
            UserProject userProject = (UserProject) obj;
            userProject.moveTo(project);
        }

        Client.getCurrentView().update();
        Client.updateMainMenu();
    }

    /**
     * Method for handling an event to the TreeView.
     * Gets the clicked MainMenuItem and passes the event to the MainMenuItem.
     * @param event The event.
     */
    private void onMainMenuItemEvent(Event event)
    {
        TreeView clickedTree = (TreeView) event.getSource();
        TreeItem clickedItem = (TreeItem) clickedTree.getSelectionModel().getSelectedItem();
        if (clickedItem == null) return;
        Object clickedItemValue = clickedItem.getValue();
        if (clickedItemValue instanceof MainMenuItem)
        {
            selectedTreeItem = clickedItem;
            ((MainMenuItem) clickedItemValue).handleEvent(event);
        }
        else
        {
            treeView.getSelectionModel().select(selectedTreeItem);
        }
    }

    /**
     * Makes a TreeItem containing a MainMenuItem.
     * @param parent The parent TreeItem to create the TreeItem from.
     * @param view The view the TreeItem links to.
     * @param contextMenu The context menu of the TreeItem.
     * @return The newly created TreeItem.
     */
    private TreeItem<MainMenuItem> makeTreeItem(TreeItem<MainMenuItem> parent, View view, ContextMenu contextMenu)
    {
        MainMenuItem mainMenuItem = new MainMenuItem(view, contextMenu);

        TreeItem<MainMenuItem> item = new TreeItem<>(mainMenuItem);
        item.setExpanded(true);
        item.setGraphic(mainMenuItem.getLabel());
        parent.getChildren().add(item);

        treeItems.add(item);
        return item;
    }
}
