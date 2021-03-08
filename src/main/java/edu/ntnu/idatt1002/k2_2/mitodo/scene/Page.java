package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import edu.ntnu.idatt1002.k2_2.mitodo.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.project.ProjectManager;
import edu.ntnu.idatt1002.k2_2.mitodo.project.Subproject;
import edu.ntnu.idatt1002.k2_2.mitodo.project.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;

public abstract class Page
{
    public abstract Scene getScene();

    protected StackPane getMenuLayout()
    {
        TreeItem<Label> root = new TreeItem<>();
        root.setExpanded(true);

        makeBranch("Quick tasks", root);

        TreeItem<Label> calendarBranch = makeBranch("Calendar", root);
        makeBranch("List", calendarBranch);
        makeBranch("Day", calendarBranch);
        makeBranch("Week", calendarBranch);
        makeBranch("Month", calendarBranch);

        makeProjectBranches(root);

        makeBranch("Settings", root);

        TreeView<Label> tree = new TreeView<>(root);
        tree.setShowRoot(false);

        StackPane layout = new StackPane();
        layout.getChildren().add(tree);

        layout.getStyleClass().add("main-menu");

        return layout;
    }

    private void makeProjectBranches(TreeItem<Label> root)
    {
        TreeItem<Label> allProjectsBranch = makeBranch("Projects", root);
        ArrayList<Project> projects = ProjectManager.getProjects();
        for (Project project : projects)
        {
            makeSubprojectBranches(project, allProjectsBranch);
        }
    }

    private void makeSubprojectBranches(Project project, TreeItem<Label> allProjects)
    {
        TreeItem<Label> projectsBranch = makeBranch(project.getTitle(), allProjects);
        ArrayList<Subproject> subprojects = project.getSubprojects();
        for (Subproject subproject : subprojects)
        {
            makeBranch(subproject.getTitle(), projectsBranch);
        }
    }

    private TreeItem<Label> makeBranch(String title, TreeItem<Label> parent)
    {
        Label label = new Label(title);
        TreeItem<Label> item = new TreeItem<>(label);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    protected StackPane getTaskLayout(Task task)
    {
        return new StackPane(new Label(task.getTitle()));
    }
}
