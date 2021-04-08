package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Public class Project
 */
public class Project {
    private UUID ID ;
    private String title;
    private ArrayList<Task> tasks;
    private ArrayList<Project> projects;

    public Project(String title)
    {
        this.title = title;
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ID = UUID.randomUUID();
    }

    public Project(){} //Empty constructor needed for some JSON related shenanigans

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
        return tasks;
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

    public Task getTaskFromAll(String title)
    {
        ArrayList<Task> allTasks = getAllTasks();
        for (Task task : allTasks)
        {
            if (task.getTitle().equals(title))
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

    public Task getTask(String title)
    {
        for (Task task : tasks)
        {
            if (task.getTitle().equals(title))
            {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task task)
    {
        tasks.add(task);
    }

    public void moveTask(UUID id, UUID projectId, Project application)
    {
        int indexOfTask = -1;
        for (Task task : tasks)
        {
            if (task.getID().equals(id))
            {
                indexOfTask = tasks.indexOf(task);
            }
        }
        application.getProject(projectId).addTask(tasks.get(indexOfTask));
        removeTask(id);
    }

    public void moveTaskByTitle(String title, String projectTitle, Project application)
    {
        int indexOfTask = -1;
        for (Task task : tasks)
        {
            if (task.getTitle().equals(title))
            {
                indexOfTask = tasks.indexOf(task);
            }
        }
        application.getProjectByTitle(projectTitle).addTask(tasks.get(indexOfTask)); //TODO: This will throw an IndexOutOfBoundsException if the title param does not match any task in the project, is that the way we want it to be? Maybe create our own exception?
        removeTaskByTitle(title);
    }

    public boolean removeTask(UUID id)
    {
        return tasks.removeIf(task -> task.getID().equals(id));
    }

    public boolean removeTaskByTitle(String title)
    {
        return tasks.removeIf(task -> task.getTitle().equals(title));
    }

    public ArrayList<Project> getProjects()
    {
        return projects;
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

    public Project getProjectByTitle(String title)
    {
        for (Project project : projects)
        {
            if (project.getTitle().equals(title))
            {
                return project;
            }
        }
        for (Project project : projects)
        {
            Project result = project.getProjectByTitle(title);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }

    public void addProject(Project project)
    {
        projects.add(project);
    }

    public void moveProject(UUID id, int index)
    {
        for (Project project : projects)
        {
            if (project.getID().equals(id))
            {
                projects.remove(project);
                projects.add(index,project);
            }
        }
    }

    public void moveProjectByTitle(String title, int index)
    {
        for (Project project : projects)
        {
            if (project.getTitle().equals(title))
            {
                projects.remove(project);
                projects.add(index,project);
            }
        }
    }

    public boolean removeProject(UUID id)
    {
        return projects.removeIf(project -> project.getID().equals(id));
    }

    public boolean removeProjectByTitle(String title)
    {
        return projects.removeIf(project -> project.getTitle().equals(title));
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