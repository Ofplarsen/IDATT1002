package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.Project;
import edu.ntnu.idatt1002.k2_2.mitodo.effects.SoundEffects;

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
    private final UUID ID;
    private String title;
    private String comments;
    private PriorityEnum priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private RepeatEnum repeat;
    private boolean isDone = false;

    private final Project parent;
    private boolean createdNextRepeatingTask = false;

    public Task(String title, Project parent)
    {
        if(title.isBlank() && title.isEmpty())
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Empty String is not accepted as title");
        }

        this.title = title.trim();
        this.parent = parent;
        this.priority = PriorityEnum.Undefined;
        this.startDate = null;
        this.dueDate = null;
        this.repeat = RepeatEnum.DoesNotRepeat;
        ID = UUID.randomUUID();
    }

    public Task(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate,RepeatEnum repeat, String comments, Project parent)
    {
        //Makes sure title is not null, nor is empty
        if(title.isBlank() || title.isEmpty())
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Empty String is not accepted as title");
        }

        //Makes sure priority is never null
        if(this.priority == null)
        {
            this.priority = PriorityEnum.Undefined;
        }

        this.parent = parent;
        this.title = title.trim();
        this.priority = priority;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.repeat = repeat == null ? RepeatEnum.DoesNotRepeat : repeat;
        this.comments = comments;
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
        this.comments = comments;
    }

    public void setTitle(String title)
    {
        if(title.isBlank() || title.isEmpty())
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Empty String is not accepted as title");
        }
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

    public void setDates(LocalDate startDate, LocalDate dueDate, RepeatEnum repeat)
    {
        if (startDate != null && dueDate != null && dueDate.isBefore(startDate))
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Can't set due date earlier than start date");
        }

        if(dueDate != null && dueDate.isBefore(LocalDate.now()))
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Can't set due date earlier than today's date");
        }

        if(startDate == null && dueDate == null && repeat != RepeatEnum.DoesNotRepeat)
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Can't repeat without either start date or due date.");
        }

        if (repeat.isShorterThanDates(startDate, dueDate))
        {
            SoundEffects.playErrorSound2();
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
            repeat = RepeatEnum.DoesNotRepeat;
        }
        return repeat;
    }

    public boolean isDone()
    {
        return isDone;
    }

    public void setDone(boolean isDone)
    {
        this.isDone = isDone;

        if (isDone && repeat != RepeatEnum.DoesNotRepeat && !createdNextRepeatingTask)
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

    public void deleteItself()
    {
        parent.removeTask(ID);
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