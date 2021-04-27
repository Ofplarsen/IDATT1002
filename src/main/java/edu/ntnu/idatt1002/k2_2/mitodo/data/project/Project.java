package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract class representing a Project with an ID, a list of tasks and a list of projects.
 */
public abstract class Project implements Serializable
{
    private final ArrayList<UserProject> userProjects = new ArrayList<>();
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final UUID ID = UUID.randomUUID();

    public abstract String getTitle();

    /**
     * @return This project's projects.
     */
    public ArrayList<UserProject> getProjects()
    {
        return new ArrayList<>(userProjects);
    }

    /**
     * @return All projects below this project. That is to say,
     * the subprojects of this project and all subprojects of the subprojects.
     */
    public ArrayList<UserProject> getAllProjects()
    {
        ArrayList<UserProject> projectBucket = new ArrayList<>(userProjects);
        for (UserProject project : userProjects)
        {
            projectBucket.addAll(project.getAllProjects());
        }
        return projectBucket;
    }

    /**
     * Adds a new project to this project.
     * @param title The title of the new project.
     * @return The newly created project.
     */
    public UserProject addProject(String title)
    {
        UserProject project = new UserProject(title, this);
        userProjects.add(0, project);
        return project;
    }

    /**
     * Protected method allowing the child class to add a project to this project.
     * @param userProject The project to add.
     */
    protected void addProject(UserProject userProject)
    {
        userProjects.add(0, userProject);
    }

    /**
     * Moves a project in this project to a different index in the list.
     * @param subproject The project to move.
     * @param newIndex The index to move to the project to.
     * @return False if the project is not in this project or the new index is equal to the old index. Otherwise true.
     */
    public boolean moveProject(UserProject subproject, int newIndex)
    {
        if (!userProjects.contains(subproject)) return false;

        int oldIndex = userProjects.indexOf(subproject);

        if (newIndex == oldIndex) return false;
        if (newIndex == oldIndex+1) return false;

        if (newIndex > oldIndex)
        {
            newIndex--;
        }

        userProjects.remove(subproject);
        userProjects.add(newIndex, subproject);
        return true;
    }

    /**
     * Removes a project from this project.
     * @param id The id of the project to remove.
     * @return True if the project was removed. Otherwise false.
     */
    public boolean removeProject(UUID id)
    {
        return userProjects.removeIf(project -> project.getID().equals(id));
    }

    /**
     * Removes a project from this project and all projects below this project.
     * @param id The id of the project to remove.
     * @return True if the project was removed. Otherwise false.
     */
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

    /**
     * @return The tasks of this project and the tasks of all projects below this project.
     */
    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>(tasks);
        taskBucket.addAll(getAllSubProjectTasks());
        return taskBucket;
    }

    /**
     * @return The tasks of all projects below this project.
     */
    private ArrayList<Task> getAllSubProjectTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>();

        for (Project project : userProjects)
        {
            taskBucket.addAll(project.getAllTasks());
        }

        return taskBucket;
    }


    /**
     * @return A shallow copy of the ArrayList of tasks in this project.
     */
    public ArrayList<Task> getTasks()
    {
        return new ArrayList<>(tasks);
    }

    /**
     * Adds a new task to this project.
     * @param title The title of the new task.
     * @return The newly created task.
     */
    public Task addTask(String title)
    {
        Task task = new Task(title, this);
        tasks.add(0, task);
        return task;
    }

    /**
     * Adds a new task to this project.
     * @param title The title of the new task.
     * @param priority The priority of the new task.
     * @param startDate The start date of the new task.
     * @param dueDate The due date of the new task.
     * @param repeat The repeating frequency of the new task.
     * @param comments The comments of the new task.
     * @return The newly created task.
     */
    public Task addTask(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate, RepeatEnum repeat, String comments)
    {
        try
        {
            Task task = new Task(title, priority, startDate, dueDate,repeat, comments, this);
            tasks.add(0, task);
            return task;
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Moves a task in this project to a different index in the list.
     * @param task The task to move.
     * @param newIndex The index to move to the task to.
     * @return False if the task is not in this project or the new index is equal to the old index. Otherwise true.
     */
    public boolean moveTask(Task task, int newIndex)
    {
        if (!tasks.contains(task)) return false;

        int oldIndex = tasks.indexOf(task);

        if (newIndex == oldIndex) return false;
        if (newIndex == oldIndex+1) return false;

        if (newIndex > oldIndex)
        {
            newIndex--;
        }

        tasks.remove(task);
        tasks.add(newIndex, task);
        return true;
    }

    /**
     * Moves a task to a different project.
     * @param task The task to move.
     * @param newParentProject The project to move the task to.
     * @return True if the task was moved to the new project and removed from the old project. Otherwise false.
     */
    public boolean moveTaskTo(Task task, Project newParentProject)
    {
        newParentProject.addTask(task.getTitle(), task.getPriority(), task.getStartDate(), task.getDueDate(),task.getRepeat(), task.getComments());
        return task.deleteItself();
    }

    /**
     * Removes a task from this project.
     * @param task The task to remove.
     * @return True if the task was removed. Otherwise false.
     */
    public boolean removeTask(Task task)
    {
        return tasks.remove(task);
    }

    /**
     * Removes a task from this project.
     * @param id The id of the task to remove.
     * @return True if the task was removed. Otherwise false.
     */
    public boolean removeTask(UUID id)
    {
        return tasks.removeIf(task -> task.getID().equals(id));
    }

    /**
     * Removes a task from this project and all projects below this project.
     * @param id The id of the task to remove.
     * @return True if the task was removed. Otherwise false.
     */
    public boolean removeTasksFromAll(UUID id){
        boolean removed = removeTask(id);
        if (removed) return true;

        for(UserProject project : userProjects)
        {
            removed = project.removeTasksFromAll(id);
            if (removed) return true;
        }
        return false;
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
