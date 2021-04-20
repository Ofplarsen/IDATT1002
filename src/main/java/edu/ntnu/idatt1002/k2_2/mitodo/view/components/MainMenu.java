package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.project.UserProject;
import edu.ntnu.idatt1002.k2_2.mitodo.view.editproject.CreateProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private final TreeView<Label> treeView;

    /**
     * Constructs a new main menu object with a TreeView.
     * @param treeView
     */
    public MainMenu(TreeView<Label> treeView)
    {
        this.treeView = treeView;
    }

    /**
     * Gets all projects from Client and updates the content of the main menu.
     */
    public void update()
    {
        TreeItem<Label> root = new TreeItem<>();
        root.setExpanded(true);

        makeTreeItem("Quick tasks", root, mouseEvent -> {
            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(Client.getRootProject());
        }, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                ProjectView projectView = (ProjectView) Client.setView("ProjectView");
                projectView.setProject(Client.getRootProject());
            }
        });

        makeTreeItem("Calendar", root, mouseEvent -> {
            Client.setView("CalendarView");
        }, keyEvent -> {
            Client.setView("CalendarView");
        });

        makeTreeItem("+", root, mouseEvent -> {
            CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
            createProjectView.setParentProject(Client.getRootProject());
        }, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
                createProjectView.setParentProject(Client.getRootProject());
            }
        });


        makeTreeItem("Settings", root, mouseEvent -> {
            Client.setView("SettingsView");
        });


        ArrayList<UserProject> projects = Client.getRootProject().getProjects();
        projects.forEach(project -> makeProjectTreeItem(root, project));

        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectFirst();

        treeView.setOnMouseClicked(mouseEvent -> {
            TreeView<Label> clickedTree = (TreeView<Label>) mouseEvent.getSource();
            TreeItem<Label> clickedItem = clickedTree.getSelectionModel().getSelectedItem();
            if (clickedItem==null) return;
            Label clickedLabel = clickedItem.getValue();
            EventHandler<MouseEvent> eventHandler = (EventHandler<MouseEvent>) clickedLabel.getOnMouseClicked();
            eventHandler.handle(mouseEvent);
        });

        treeView.setOnKeyPressed(keyEvent -> {

            switch (keyEvent.getCode()) {
                case ENTER:
                    TreeView<Label> selectedTree = (TreeView<Label>) keyEvent.getSource();
                    TreeItem<Label> selectedItem = selectedTree.getSelectionModel().getSelectedItem();
                    if(selectedItem == null) return;
                    Label selectedLabel = selectedItem.getValue();
                    EventHandler<Event> eventHandle = (EventHandler<Event>) selectedLabel.getOnKeyPressed();
                    eventHandle.handle(keyEvent);
                    break;
            }

        });
    }

    /**
     * Makes a Treeitem for the given project and calls itself recursively for the sub-projects.
     * @param parent The parent TreeItem to create the Treeitem from.
     * @param project The project to create a Treeitem for.
     */
    private void makeProjectTreeItem(TreeItem<Label> parent, Project project)
    {
        TreeItem<Label> projectItem = makeTreeItem(project.getTitle(), parent, mouseEvent -> {
            ProjectView projectView = (ProjectView) Client.setView("ProjectView");
            projectView.setProject(project);
        }, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                ProjectView projectView = (ProjectView) Client.setView("ProjectView");
                projectView.setProject(project);
            }
        });
        //TODO: Turn into right-click-menu-option
        makeTreeItem("+", parent, mouseEvent -> {
            CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
            createProjectView.setParentProject(project);
        }, keyEvent -> {
            CreateProjectView createProjectView = (CreateProjectView) Client.setView("CreateProjectView");
            createProjectView.setParentProject(project);
        });
        project.getProjects().forEach(subProject -> makeProjectTreeItem(projectItem, subProject));
    }

    /**
     * Creates a new Treeitem.
     * @param title The title of the Treeitem.
     * @param parent The parent TreeItem to create the Treeitem from.
     * @param eventHandler The eventHandler for when the Treeitem is clicked.
     * @return The TreeItem it created.
     */
    private TreeItem<Label> makeTreeItem(String title, TreeItem<Label> parent, EventHandler<MouseEvent> eventHandler , EventHandler<KeyEvent> keyEventEventHandler)
    {
        Label label = new Label(title);
        label.setOnMouseClicked(eventHandler);
        label.setOnKeyPressed(keyEventEventHandler);
        TreeItem<Label> item = new TreeItem<>(label);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}
