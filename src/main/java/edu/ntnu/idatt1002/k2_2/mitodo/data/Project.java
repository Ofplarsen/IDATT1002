package edu.ntnu.idatt1002.k2_2.mitodo.data;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Public class Project
 */
public class Project implements Serializable
{
    private final UUID ID;
    private String title;
    private final ArrayList<Task> tasks;
    private final ArrayList<Project> projects;
    private final Project parent;

    public Project(String title, Project parent)
    {
        if(title.isEmpty() || title.isBlank())
        {
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(projectAlreadyCreated(title))
        {
            throw new IllegalArgumentException("Project already created");
        }

        this.title = title.trim();
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ID = UUID.randomUUID();
        this.parent = parent;
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
        if(title.isEmpty() || title.isBlank())
        {
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(projectAlreadyCreated(title))
        {
            throw new IllegalArgumentException("Project already created");
        }
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

    public ArrayList<Task> getAllSubProjectTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>();

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
         Task task = new Task(title, this);
         tasks.add(task);
         return task;
    }

    public void moveTask(UUID taskID, UUID projectID)
    {
         Task task = this.getTask(taskID);
         Client.getRootProject().getProject(projectID).addTask(task.getTitle(), task.getPriority(), task.getStartDate(), task.getDueDate(),task.getRepeat(), task.getComments());
         removeTask(taskID);
    }

    public Task addTask(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate,RepeatEnum repeat, String comments)
    {
        try
        {
            Task task = new Task(title, priority, startDate, dueDate,repeat, comments, this);
            tasks.add(task);
            return task;
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean removeTask(UUID id)
    {
        return tasks.removeIf(task -> task.getID().equals(id));
    }

    public boolean removeTaskFromAll(UUID id)
    {
        boolean removed = removeTask(id);
        if (removed) return true;

        for(Project project : projects)
        {
            removed = project.removeTaskFromAll(id);
            if (removed) return true;
        }
        return false;
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
        Project project = new Project(title, this);
        projects.add(project);
        return project;
    }

    public boolean projectAlreadyCreated(String title)
    {
        if(parent == null || parent.getProjects().size() == 0)
        {
            return false;
        }
        return parent.getProjects().stream().anyMatch(p -> p.getTitle().equalsIgnoreCase(title));
    }

    public boolean removeProject(UUID id)
    {
        return projects.removeIf(project -> project.getID().equals(id));
    }

    public boolean removeProjectFromAll(UUID id)
    {
        boolean removed = removeProject(id);
        if (removed) return true;

        for(Project project : projects)
        {
            removed = project.removeProjectFromAll(id);
            if (removed) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(ID, project.ID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ID);
    }

    public Project getParent(){
        return parent;
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