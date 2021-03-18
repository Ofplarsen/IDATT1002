package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.QuickTasks;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Settings;

/**
 * Class with static methods for managing data (projects, quickTasks and settings).
 */
import java.util.ArrayList;

public class DataManager
{
    private static final ArrayList<Project> projects = new ArrayList<>();
    private static final QuickTasks quickTasks = new QuickTasks();
    private static final Settings settings = new Settings();

    /**
     * Adds a new Project.
     * @param title
     * @return The new Project.
     */
    public static Project addProject(String title)
    {
        Project project = new Project(title);
        projects.add(project);
        return project;
    }

    /**
     * Removes a project.
     * @param project
     */
    public static void removeProject(Project project)
    {
        projects.remove(project);
    }

    /**
     * @return The ArrayList of all projects.
     */
    public static ArrayList<Project> getProjects()
    {
        return projects;
    }

    /**
     * @return The subproject quickTasks.
     */
    public static QuickTasks getQuickTasks()
    {
        return quickTasks;
    }

    /**
     * @return The settings.
     */
    public static Settings getSettings()
    {
        return settings;
    }
}
