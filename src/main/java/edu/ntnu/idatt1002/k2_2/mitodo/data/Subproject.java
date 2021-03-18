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

<<<<<<< HEAD
    /**
     * Constructs a new Subproject with title and color.
     * @param title The subproject title.
     * @param color The color of the subproject.
     */
=======
    public Subproject(String title) {
        this.title = title;
        this.color = color.BLUE;
    }

>>>>>>> 54783d99efa9bfaf2225fef3a002e5c6bf1bbdce
    public Subproject(String title, Color color)
    {
        this.title = title;
        this.color = color;

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
        if(tasks.contains(new Task(title,priority))){
            throw new IllegalArgumentException("Tasks already added");
        }
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
<<<<<<< HEAD
        return tasks.remove(task);
=======
        if(!tasks.contains(task)){
            throw new IllegalArgumentException("Can't find task");
        }
        tasks.remove(task);
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
>>>>>>> 54783d99efa9bfaf2225fef3a002e5c6bf1bbdce
    }

    /**
     * @return The ArrayList of tasks in this subproject.
     */
    public ArrayList<Task> getTasks()
    {
        return tasks;
    }


}
