package edu.ntnu.idatt1002.k2_2.mitodo.scene;

import edu.ntnu.idatt1002.k2_2.mitodo.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.project.ProjectManager;
import edu.ntnu.idatt1002.k2_2.mitodo.project.Subproject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class MainMenu
{
    private final String currentPageTitle;
    private TreeItem<Button> currentPageBranch;

    public MainMenu(String currentPageTitle)
    {
        this.currentPageTitle = currentPageTitle;
    }

    protected StackPane getMenuLayout()
    {
        TreeItem<Button> root = new TreeItem<>();
        root.setExpanded(true);

        currentPageBranch = makeBranch("Quick tasks", root, event -> {
            SceneManager.setPage(new QuickTasksPage());
        });

        TreeItem<Button> calendarBranch = makeBranch("Calendar", root, null);
        makeBranch("List", calendarBranch, event -> {
            //SceneManager.setPage(new CalendarListPage());
        });
        makeBranch("Day", calendarBranch, event -> {
            //SceneManager.setPage(new CalendarDayPage());
        });
        makeBranch("Week", calendarBranch, event -> {
            //SceneManager.setPage(new CalendarWeekPage());
        });
        makeBranch("Month", calendarBranch, event -> {
            //SceneManager.setPage(new CalendarMonthPage());
        });

        makeProjectBranches(root);

        makeBranch("Settings", root, event -> {
            //SceneManager.setPage(new SettingsPage());
        });

        TreeView<Button> tree = new TreeView<>(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue != oldValue)
            {
                newValue.getValue().fire();
            }
        });
        tree.getSelectionModel().select(currentPageBranch);

        StackPane layout = new StackPane();
        layout.getChildren().add(tree);
        layout.getStyleClass().add("main-menu");
        layout.getStylesheets().add("css/menu.css");

        return layout;
    }

    private void makeProjectBranches(TreeItem<Button> root)
    {
        TreeItem<Button> allProjectsBranch = makeBranch("Projects", root, event -> {
            //SceneManager.setPage(new AllProjectsPage());
        });
        ArrayList<Project> projects = ProjectManager.getProjects();
        for (Project project : projects)
        {
            makeSubprojectBranches(project, allProjectsBranch);
        }
    }

    private void makeSubprojectBranches(Project project, TreeItem<Button> allProjects)
    {
        TreeItem<Button> projectBranch = makeBranch(project.getTitle(), allProjects, event -> {
            //SceneManager.setPage(new ProjectPage(project));
        });
        ArrayList<Subproject> subprojects = project.getSubprojects();
        for (Subproject subproject : subprojects)
        {
            makeBranch(subproject.getTitle(), projectBranch, event -> {
                SceneManager.setPage(new SubprojectPage(subproject));
            });
        }
    }

    private TreeItem<Button> makeBranch(String title, TreeItem<Button> parent, EventHandler<ActionEvent> eventEventHandler)
    {
        Button button = new Button(title);
        if (eventEventHandler != null)
        {
            button.setOnAction(eventEventHandler);
        }
        TreeItem<Button> item = new TreeItem<>(button);
        item.setExpanded(true);
        if (title.equals(currentPageTitle))
        {
            currentPageBranch = item;
        }

        parent.getChildren().add(item);
        return item;
    }
}
