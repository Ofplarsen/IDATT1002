package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A class representing a task consisting of an id, title, comments, priority and end/start date repeating period and is-done status.
 * When the user creates a task, it gets an unique Id and the user gives the task an title
 * The unique id is created from the class UUID, which is a Java class that among other things lets you get a random
 * 128-bit number. This makes it as good as impossible for two task get the same id. This is implemented, because the user
 * should be able to create two tasks with the same title.
 */
public class Task implements Serializable
{
    private final UUID ID;
    private String title;
    private String comments;
    private PriorityEnum priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private RepeatEnum repeat;
    private boolean isDone = false;
    private final static int TASK_MAX_LENGTH = 28;
    private final Project parent;
    private boolean createdNextRepeatingTask = false;

    /**
     * Constructs a new task.
     * @param title The title of the task.
     * @param parent The parent project of the task.
     * @throws IllegalArgumentException If the length of the title is 0 or above 28.
     */
    public Task(String title, Project parent) throws IllegalArgumentException
    {
        if(title.isBlank() && title.isEmpty())
        {
            throw new IllegalArgumentException("Empty String is not accepted as title");
        }
        if (title.length() > TASK_MAX_LENGTH){
            throw new IllegalArgumentException("Task title must be below " + TASK_MAX_LENGTH + " characters.");
        }

        this.title = title.trim();
        this.parent = parent;
        this.priority = PriorityEnum.UNDEFINED;
        this.startDate = null;
        this.dueDate = null;
        this.repeat = RepeatEnum.DOES_NOT_REPEAT;
        ID = UUID.randomUUID();
    }

    /**
     * Constructs a new task.
     * @param title The title of the new task.
     * @param priority The priority of the new task.
     * @param startDate The start date of the new task.
     * @param dueDate The due date of the new task.
     * @param repeat The repeating frequency of the new task.
     * @param comments The comments of the new task.
     * @param parent The parent project of the task.
     * @throws IllegalArgumentException If the length of the title is 0 or above 28,
     * the due date is earlier than the start date,
     * the due date is earlier than today's date, trying to repeat without start date or due date
     * or the time between start date and due date is longer than the repeating period.
     */
    public Task(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate,RepeatEnum repeat, String comments, Project parent) throws IllegalArgumentException
    {
        //Makes sure title is not null, nor is empty
        if(title.isBlank() || title.isEmpty())
        {
            throw new IllegalArgumentException("Empty String is not accepted as title");
        }
        //Title no longer than 28
        if (title.length() > TASK_MAX_LENGTH){
            throw new IllegalArgumentException("Task title must be below " + TASK_MAX_LENGTH+ " characters.");
        }

        this.parent = parent;
        this.title = title.trim();
        this.priority = priority == null ? PriorityEnum.UNDEFINED : priority;
        this.repeat = repeat == null ? RepeatEnum.DOES_NOT_REPEAT : repeat;

        setDates(startDate, dueDate, this.repeat);

        if(comments != null)
        {
            this.comments = comments.trim();
        }

        ID = UUID.randomUUID();
    }

    public Project getParent()
    {
        return parent;
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
        if(comments != null)
        {
            this.comments = comments.trim();
        }
        else
        {
            this.comments = null;
        }
    }

    /**
     * Sets the title of the task.
     * @param title The title of the new task.
     * @throws IllegalArgumentException If the length of the title is 0 or above 28.
     */
    public void setTitle(String title) throws IllegalArgumentException
    {
        if(title.isBlank() || title.isEmpty())
        {
            throw new IllegalArgumentException("Empty String is not accepted as title");
        }
        if (title.length() > TASK_MAX_LENGTH){
            throw new IllegalArgumentException("Task title must be below " + TASK_MAX_LENGTH+ " characters.");
        }
        this.title = title.trim();
    }

    public PriorityEnum getPriority()
    {
        return priority;
    }

    public void setPriority(PriorityEnum priority)
    {
        this.priority = priority == null ? PriorityEnum.UNDEFINED : priority;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * Sets the start date, due date and repeating frequency.
     * @param startDate The start date.
     * @param dueDate The due date.
     * @param repeat The repeating frequency.
     * @throws IllegalArgumentException If the due date is earlier than the start date,
     * the due date is earlier than today's date, trying to repeat without start date or due date
     * or the time between start date and due date is longer than the repeating period.
     */
    public void setDates(LocalDate startDate, LocalDate dueDate, RepeatEnum repeat) throws IllegalArgumentException
    {
        if (startDate != null && dueDate != null && dueDate.isBefore(startDate))
        {
            throw new IllegalArgumentException("Can't set due date earlier than start date");
        }

        if(dueDate != null && dueDate.isBefore(LocalDate.now()))
        {
            throw new IllegalArgumentException("Can't set due date earlier than today's date");
        }

        if(startDate == null && dueDate == null && repeat != RepeatEnum.DOES_NOT_REPEAT)
        {
            throw new IllegalArgumentException("Can't repeat without either start date or due date.");
        }

        if (repeat.isShorterThanDates(startDate, dueDate))
        {
            throw new IllegalArgumentException("Time between start date and due date can't be longer than the repeating period.");
        }

        this.startDate = startDate;
        this.dueDate = dueDate;
        this.repeat = repeat;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
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

    public RepeatEnum getRepeat()
    {
        if (repeat == null)
        {
            repeat = RepeatEnum.DOES_NOT_REPEAT;
        }
        return repeat;
    }

    public boolean isDone()
    {
        return isDone;
    }

    /**
     * Sets the is-done status. Creates the next task if the task is a repeating task.
     * @param isDone The new is-done status.
     */
    public void setDone(boolean isDone)
    {
        this.isDone = isDone;

        if (isDone && repeat != RepeatEnum.DOES_NOT_REPEAT && !createdNextRepeatingTask)
        {
            LocalDate nextStartDate = repeat.getNextDate(startDate);
            LocalDate nextDueDate = repeat.getNextDate(dueDate);
            parent.addTask(title, priority, nextStartDate, nextDueDate, repeat, comments);
            createdNextRepeatingTask = true;
        }
    }

    public void toggleIsDone()
    {
        setDone(!isDone);
    }

    /**
     * Removes itself from the parent project.
     * @return True if the task was removed. Otherwise false.
     */
    public boolean deleteItself()
    {
        return parent.removeTask(ID);
    }

    public boolean isOverdue()
    {
        if (dueDate == null) return false;
        return dueDate.isBefore(LocalDate.now()) && !isDone;
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
                "\nrepeat=" + repeat +
                "\nisDone=" + isDone +
                "\ncreatedNextRepeatingTask=" + createdNextRepeatingTask +
                "\nproject=" + parent +
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