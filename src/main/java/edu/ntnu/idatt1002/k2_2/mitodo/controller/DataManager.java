package edu.ntnu.idatt1002.k2_2.mitodo.controller;

import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.data.QuickTasks;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Settings;

import java.util.ArrayList;

public class DataManager
{
    private static final ArrayList<Project> projects = new ArrayList<>();
    private static final QuickTasks quickTasks = new QuickTasks();
    private static final Settings settings = new Settings();

    public static Project addProject(String title)
    {
        Project project = new Project(title);
        projects.add(project);
        return project;
    }

    public static void removeProject(Project project)
    {
        projects.remove(project);
    }

    public static ArrayList<Project> getProjects()
    {
        return projects;
    }

    public static QuickTasks getQuickTasks()
    {
        return quickTasks;
    }

    public static Settings getSettings()
    {
        return settings;
    }
}
