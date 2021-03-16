package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.controller.DataManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Subproject;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;

public class MainMenu
{
    private final String currentPageTitle;
    private TreeItem<Label> currentPageBranch;

    public MainMenu(String currentPageTitle)
    {
        this.currentPageTitle = currentPageTitle;
    }

    public void editMenuLayout(Parent parent)
    {
        TreeItem<Label> root = new TreeItem<>();
        root.setExpanded(true);

        currentPageBranch = makeBranch("Quick tasks", root);

        TreeItem<Label> calendarBranch = makeBranch("Calendar", root);
        makeBranch("List", calendarBranch);
        makeBranch("Day", calendarBranch);
        makeBranch("Week", calendarBranch);
        makeBranch("Month", calendarBranch);

        makeProjectBranches(root);

        makeBranch("Settings", root);

        TreeView<Label> tree = (TreeView<Label>) parent.lookup("#mainMenu");;
        tree.setRoot(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().select(currentPageBranch);
    }

    private void makeProjectBranches(TreeItem<Label> root)
    {
        TreeItem<Label> allProjectsBranch = makeBranch("Projects", root);
        ArrayList<Project> projects = DataManager.getProjects();
        for (Project project : projects)
        {
            makeSubprojectBranches(project, allProjectsBranch);
        }
    }

    private void makeSubprojectBranches(Project project, TreeItem<Label> allProjects)
    {
        TreeItem<Label> projectBranch = makeBranch(project.getTitle(), allProjects);
        ArrayList<Subproject> subprojects = project.getSubprojects();
        for (Subproject subproject : subprojects)
        {
            makeBranch(subproject.getTitle(), projectBranch);
        }
    }

    private TreeItem<Label> makeBranch(String title, TreeItem<Label> parent)
    {
        Label label = new Label(title);
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
