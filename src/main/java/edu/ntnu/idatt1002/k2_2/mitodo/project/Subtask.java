package edu.ntnu.idatt1002.k2_2.mitodo.project;

public class Subtask
{
    private String title;

    protected Subtask(String title)
    {
        setTitle(title);
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
}
