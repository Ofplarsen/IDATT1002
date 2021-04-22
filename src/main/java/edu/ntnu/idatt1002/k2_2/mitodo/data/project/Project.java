package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public abstract class Project implements Serializable
{
    private final ArrayList<UserProject> userProjects = new ArrayList<>();
    private final ArrayList<Task> tasks = new ArrayList<>();
    protected final static int MAX_TITLE_SIZE = 28;
    private final UUID ID = UUID.randomUUID();

    public abstract String getTitle();

    public ArrayList<UserProject> getProjects()
    {
        return new ArrayList<>(userProjects);
    }

    public ArrayList<UserProject> getAllProjects()
    {
        ArrayList<UserProject> projectBucket = new ArrayList<>(userProjects);
        for (UserProject project : userProjects)
        {
            projectBucket.addAll(project.getAllProjects());
        }
        return projectBucket;
    }

    public UserProject getProject(UUID id)
    {
        for (UserProject project : userProjects)
        {
            if (project.getID().equals(id))
            {
                return project;
            }
        }
        return null;
    }

    public UserProject addProject(String title)
    {
        UserProject project = new UserProject(title, this);
        userProjects.add(project);
        return project;
    }

    public boolean removeProject(UUID id)
    {
        return userProjects.removeIf(project -> project.getID().equals(id));
    }

    public boolean removeProjectFromAll(UUID id)
    {
        boolean removed = removeProject(id);
        if (removed) return true;

        for(UserProject project : userProjects)
        {
            removed = project.removeProjectFromAll(id);
            if (removed) return true;
        }
        return false;
    }

    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>(tasks);
        taskBucket.addAll(getAllSubProjectTasks());
        return taskBucket;
    }

    public ArrayList<Task> getAllSubProjectTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>();

        for (Project project : userProjects)
        {
            taskBucket.addAll(project.getAllTasks());
        }

        return taskBucket;
    }

    public boolean removeTaskFromAll(UUID id)
    {
        boolean removed = removeTask(id);
        if (removed) return true;

        for(Project project : userProjects)
        {
            removed = project.removeTaskFromAll(id);
            if (removed) return true;
        }
        return false;
    }

    public ArrayList<Task> getTasks()
    {
        return new ArrayList<>(tasks);
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

    public void moveTask(Task task, int newIndex)
    {
        if (!tasks.contains(task)) return;

        int oldIndex = tasks.indexOf(task);

        if (newIndex == oldIndex) return;
        if (newIndex == oldIndex+1) return;

        if (newIndex > oldIndex)
        {
            newIndex--;
        }

        tasks.remove(task);
        tasks.add(newIndex, task);
    }

    public void moveTask(Task task, Project project)
    {
        project.addTask(task.getTitle(), task.getPriority(), task.getStartDate(), task.getDueDate(),task.getRepeat(), task.getComments());
        task.deleteItself();
    }

    public Task addTask(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate, RepeatEnum repeat, String comments)
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

    public UUID getID()
    {
        return ID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (UserProject) o;
        return Objects.equals(ID, project.ID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ID);
    }
}
