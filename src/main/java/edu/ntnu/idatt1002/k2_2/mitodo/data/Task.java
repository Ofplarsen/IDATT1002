package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.Date;
import java.util.UUID;

/**
 * Public class Task
 * The Task class consists of an id, title, priority and end/start date
 * When the user creates a task, it gets an unique Id and the user gives the task an title
 * The unique id is created from the class UUID, which is a Java class that among other things lets you get a random
 * 128-bit number. This makes it as good as impossible for two task get the same id. This is implemented, because the user
 * should be able to create two tasks with the same title.
 */
public class Task {
    private UUID id;
    private String title;
    private PriorityEnum priority;
    private Date startDate;
    private Date dueDate;

    public Task(String title) {
        this.title = title;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "\nTask{" +
                "\nid=" + id +
                "\ntitle='" + title +
                "\npriority=" + priority +
                "\nstartDate=" + startDate +
                "\ndueDate=" + dueDate +
                "\n}";
    }
}
