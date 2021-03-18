package edu.ntnu.idatt1002.k2_2.mitodo.data;

<<<<<<< HEAD
/**
 * Class for subtask.
 */
=======
import java.util.Objects;

>>>>>>> 54783d99efa9bfaf2225fef3a002e5c6bf1bbdce
public class Subtask
{
    private String title;

    /**
     * Constructs a new subtask.
     * @param title The subtask title.
     */
    public Subtask(String title)
    {
        this.title = title;
    }

    /**
     * Sets the subtask title.
     * @param title The subtask title.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets the subtask title.
     * @return The subtask title.
     */
    public String getTitle()
    {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Subtask)){
            return false;
        }
        Subtask subtask = (Subtask) o;
        return Objects.equals(title, subtask.title);
    }
}
