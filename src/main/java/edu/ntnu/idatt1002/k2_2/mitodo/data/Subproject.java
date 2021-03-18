package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Class for subprojects with method to edit tasks.
 */
public class Subproject
{
    private String title;
    private Color color;
    private ArrayList<Task> tasks;

    /**
     * Constructs a new Subproject with title and color.
     * @param title The subproject title.
     * @param color The color of the subproject.
     */
    public Subproject(String title, Color color)
    {
        setTitle(title);
        setColor(color);

        tasks = new ArrayList<>();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }

    /**
     * Adds a new task with a title and priority.
     * @param title The task title.
     * @param priority The task priority.
     * @return The created task.
     */
    public Task addTask(String title, PriorityEnum priority)
    {
        Task task = new Task(title, priority);
        tasks.add(task);
        return task;
    }

    /**
     * Removes a task from this subproject.
     * @param task The task to remove.
     * @return true if this subproject contained the task.
     */
    public boolean removeTask(Task task)
    {
        return tasks.remove(task);
    }

    /**
     * @return The ArrayList of tasks in this subproject.
     */
    public ArrayList<Task> getTasks()
    {
        return tasks;
    }
}
