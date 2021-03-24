package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.Date;
import java.util.UUID;

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
