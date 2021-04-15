package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Public class Task
 * The Task class consists of an id, title, priority and end/start date
 * When the user creates a task, it gets an unique Id and the user gives the task an title
 * The unique id is created from the class UUID, which is a Java class that among other things lets you get a random
 * 128-bit number. This makes it as good as impossible for two task get the same id. This is implemented, because the user
 * should be able to create two tasks with the same title.
 */
public class Task implements Serializable
{
    private UUID ID; //We have to do it like this instead of "private final UUID ID = UUID.randomUUID();" if we want JSON
    private String title;
    private String comments;
    private PriorityEnum priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean isDone = false;

    public Task(String title)
    {
        this.title = title;
        this.priority = PriorityEnum.UNDEFINED;
        this.startDate = null;
        this.dueDate = null;
        ID = UUID.randomUUID(); //Setting UUID here for JSON reasons
    }

    public Task(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate, String comments){


        this.title = title;
        this.priority = priority;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.comments = comments;
        ID = UUID.randomUUID(); //Setting UUID here for JSON reasons
    }

    private Task(){} //JSON needs this

    private void setID(UUID ID) { //JSON needs this
        this.ID = ID;
    }

    public UUID getID()
    {
        return ID;
    }

    public String getTitle()
    {
        return title;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public PriorityEnum getPriority()
    {
        return priority;
    }

    public void setPriority(PriorityEnum priority)
    {
        this.priority = priority;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        //TODO add hvis begge er tilfelle, også på due date

        this.startDate = startDate;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate)
    {
        if(dueDate != null && dueDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Can't set due date earlier than today's date");
        }

        this.dueDate = dueDate;
    }

    public String getStartDateAsString()
    {
        return getDateAsString(startDate);
    }

    public String getDueDateAsString()
    {
        return getDateAsString(dueDate);
    }

    private String getDateAsString(LocalDate date)
    {
        if (date == null)
        {
            return "";
        }
        String asString = "" + date.getDayOfMonth();
        asString += "." + date.getMonthValue();
        asString += "." + date.getYear();
        return asString;
    }

    public boolean isDone()
    {
        return isDone;
    }

    public void setDone(boolean isDone)
    {
        this.isDone = isDone;
    }

    public void toggleIsDone()
    {
        isDone = !isDone;
    }

    @Override
    public String toString()
    {
        return "\nTask{" +
                "\nid=" + ID +
                "\ntitle='" + title +
                "\ncomments='" + comments +
                "\npriority=" + priority +
                "\nstartDate=" + startDate +
                "\ndueDate=" + dueDate +
                "\n}";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return ID.equals(task.ID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ID);
    }

}
