package edu.ntnu.idatt1002.k2_2.mitodo.data;

/**
 * Class for subtask.
 */
public class Subtask
{
    private String title;

    /**
     * Constructs a new subtask.
     * @param title The subtask title.
     */
    public Subtask(String title)
    {
        setTitle(title);
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
}
