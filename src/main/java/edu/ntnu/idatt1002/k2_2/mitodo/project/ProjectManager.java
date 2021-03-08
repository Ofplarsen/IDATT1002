package edu.ntnu.idatt1002.k2_2.mitodo.project;

import edu.ntnu.idatt1002.k2_2.mitodo.file.FileManager;

import java.util.ArrayList;

public class ProjectManager
{
    private ArrayList<Project> projects;
    private final QuickTasks quickTasks;

    private static ProjectManager instance;

    private ProjectManager()
    {
        projects = new ArrayList<>();
        quickTasks = new QuickTasks();
    }

    public static ProjectManager get()
    {
        if (instance == null)
        {
            instance = FileManager.loadProjectManager();
        }
        if (instance == null)
        {
            instance = new ProjectManager();
        }
        return instance;
    }

    public static Project addProject(String title)
    {
        Project project = new Project(title);
        get().projects.add(project);
        return project;
    }

    public static void removeProject(Project project)
    {
        get().projects.remove(project);
    }

    public static void setProjects(ArrayList<Project> projects)
    {
        get().projects = projects;
    }

    public static ArrayList<Project> getProjects()
    {
        return get().projects;
    }

    public static QuickTasks getQuickTasks()
    {
        return get().quickTasks;
    }
}
