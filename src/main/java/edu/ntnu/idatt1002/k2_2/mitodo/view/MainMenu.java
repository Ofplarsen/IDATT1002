/*
package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.controller.PageManager;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MainMenu
{
    private final String currentPageTitle;
    private TreeItem<Label> currentPageBranch;

    */
/**
     * Constructs a new MainMenu
     * @param currentPageTitle The current page title
     *//*

    public MainMenu(String currentPageTitle)
    {
        this.currentPageTitle = currentPageTitle;
    }

    */
/**
     * Adds menu items to the menu
     * @param tree The menu TreeView
     *//*

    public void editMenuLayout(TreeView tree)
    {
        TreeItem<Label> root = new TreeItem<>();
        root.setExpanded(true);

        makeBranch("Quick tasks", root, mouseEvent -> {
            PageManager.setPage(SubprojectPage.getNewPage(DataManager.getQuickTasks()));
        });

        makeBranch("Calendar", root, mouseEvent -> {
            //PageManager.setPage(new CalendarPage());
        });

        makeProjectBranches(root);

        makeBranch("Settings", root, mouseEvent -> {
            //PageManager.setPage(new SettingsPage());
        });

        tree.setRoot(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().select(currentPageBranch);

        tree.setOnMouseClicked(mouseEvent -> {
            TreeView<Label> clickedTree = (TreeView<Label>) mouseEvent.getSource();
            TreeItem<Label> clickedItem = clickedTree.getSelectionModel().getSelectedItem();
            Label clickedLabel = clickedItem.getValue();
            EventHandler<MouseEvent> eventHandler = (EventHandler<MouseEvent>) clickedLabel.getOnMouseClicked();
            eventHandler.handle(mouseEvent);
        });
    }

    private void makeProjectBranches(TreeItem<Label> root)
    {
        TreeItem<Label> allProjectsBranch = makeBranch("Projects", root, mouseEvent -> {
            //PageManager.setPage(new AllProjectsPage());
        });
        ArrayList<Project> projects = DataManager.getProjects();
        for (Project project : projects)
        {
            makeSubprojectBranches(project, allProjectsBranch);
        }
    }

    private void makeSubprojectBranches(Project project, TreeItem<Label> allProjects)
    {
        TreeItem<Label> projectBranch = makeBranch(project.getTitle(), allProjects, mouseEvent -> {
            //PageManager.setPage(new ProjectPage(project));
        });
        ArrayList<Subproject> subprojects = project.getSubprojects();
        for (Subproject subproject : subprojects)
        {
            makeBranch(subproject.getTitle(), projectBranch, mouseEvent -> {
                PageManager.setPage(SubprojectPage.getNewPage(subproject));
            });
        }
    }

    private TreeItem<Label> makeBranch(String title, TreeItem<Label> parent, EventHandler<MouseEvent> eventHandler)
    {
        Label label = new Label(title);
        label.setOnMouseClicked(eventHandler);
        TreeItem<Label> item = new TreeItem<>(label);
        item.setExpanded(true);
        if (title.equals(currentPageTitle))
        {
            currentPageBranch = item;
        }

        parent.getChildren().add(item);
        return item;
    }
}
*/
