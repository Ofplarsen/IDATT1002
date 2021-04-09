package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Public class Project
 */
public class Project implements Serializable
{
    private final UUID ID;
    private String title;
    private final ArrayList<Task> tasks;
    private final ArrayList<Project> projects;

    public Project(String title)
    {
        this.title = title;
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ID = UUID.randomUUID();
    }

    public UUID getID()
    {
        return ID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public ArrayList<Task> getTasks()
    {
        return new ArrayList<>(tasks);
    }

    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>(tasks);

        for (Project project : projects)
        {
            taskBucket.addAll(project.getAllTasks());
        }

        return taskBucket;
    }

    public Task getTaskFromAll(UUID ID)
    {
        ArrayList<Task> allTasks = getAllTasks();
        for (Task task : allTasks)
        {
            if (task.getID().equals(ID))
            {
                return task;
            }
        }
        return null;
    }

    public Task getTask(UUID id)
    {
        for (Task task : tasks)
        {
            if (task.getID().equals(id))
            {
                return task;
            }
        }

        return null;
    }

    public Task addTask(String title)
    {
         Task task = new Task(title);
         tasks.add(task);
         return task;
    }

    public Task addTask(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate)
    {
        Task task = new Task(title, priority, startDate, dueDate);
        tasks.add(task);
        return task;
    }

    public boolean removeTask(UUID id)
    {
        return tasks.removeIf(task -> task.getID().equals(id));
    }

    public ArrayList<Project> getProjects()
    {
        return new ArrayList<>(projects);
    }

    public Project getProject(UUID id)
    {
        for (Project project : projects)
        {
            if (project.getID().equals(id))
            {
                return project;
            }
        }
        for (Project project : projects)
        {
            Project result = project.getProject(id);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }

    public Project addProject(String title)
    {
        Project project = new Project(title);
        projects.add(project);
        return project;
    }

    public boolean removeProject(UUID id)
    {
        return projects.removeIf(project -> project.getID().equals(id));
    }

    @Override
    public String toString()
    {
        return "\nProject{" +
                "\nid=" + ID +
                "\ntitle='" + title +
                "\ntasks=" + tasks +
                "\nprojects=" + projects +
                "\n}";
    }
}