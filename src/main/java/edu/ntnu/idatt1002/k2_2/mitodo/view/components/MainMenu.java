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
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;

/**
 * The main menu has a treeView that it fills with buttons to all pages.
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
     * @param parent The parent TreeItem to create the Treeitem from.
     * @param project The project to create a Treeitem for.
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
        ContextMenu contextMenu = new ContextMenu(editProject, addSubProject, addTask);

        TreeItem<MainMenuItem> projectItem = makeTreeItem(parent, projectView, contextMenu);

        projectItem.getValue().setOnDragDetected(event ->
        {
            Dragboard db = projectItem.getValue().getLabel().startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cc = new ClipboardContent();
            cc.putString(project.getTitle());
            db.setContent(cc);
            DragAndDropManager.setValue(project);
        });
        projectItem.getValue().setOnDragOver(event -> onDragOverProjectView(project, event));
        projectItem.getValue().setOnDragDropped(event -> onDragDroppedOnProjectView(project));

        return projectItem;
    }

    private void makeAllProjectTreeItems(TreeItem<MainMenuItem> parent, Project project)
    {
        TreeItem<MainMenuItem> projectItem = makeProjectTreeItem(parent, project);
        project.getProjects().forEach(subProject -> makeAllProjectTreeItems(projectItem, subProject));
    }

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

    private void onDragDroppedOnProjectView(Project project)
    {
        Object obj = DragAndDropManager.getValue();
        if (obj instanceof Task)
        {
            Task task = (Task) obj;
            project.addTask(task.getTitle(), task.getPriority(), task.getStartDate(), task.getDueDate(),task.getRepeat(), task.getComments());
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
