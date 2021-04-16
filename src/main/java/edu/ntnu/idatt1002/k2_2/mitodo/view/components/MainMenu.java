package edu.ntnu.idatt1002.k2_2.mitodo.view.components;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.view.EditProjectView;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * The main menu has a treeView that it fills with buttons to all pages.
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
            projectView.setProject(Client.getQuickTasks());
        });

        makeTreeItem("+", root, mouseEvent -> {
            EditProjectView editProjectView = (EditProjectView) Client.setView("EditProjectView");
            editProjectView.setParentProject(Client.getRootProject());
        });

        makeTreeItem("Calendar", root, mouseEvent -> {
            Client.setView("CalendarView");
        });



        /*
        makeBranch("Settings", root, mouseEvent -> {
            Client.setView("SettingsView");
        });
         */

        ArrayList<Project> projects = Client.getRootProject().getProjects();
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
        });
        //TODO: Turn into right-click-menu-option
        makeTreeItem("+", parent, mouseEvent -> {
            EditProjectView editProjectView = (EditProjectView) Client.setView("EditProjectView");
            editProjectView.setParentProject(project);
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
    private TreeItem<Label> makeTreeItem(String title, TreeItem<Label> parent, EventHandler<MouseEvent> eventHandler)
    {
        Label label = new Label(title);
        label.setOnMouseClicked(eventHandler);
        TreeItem<Label> item = new TreeItem<>(label);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}
