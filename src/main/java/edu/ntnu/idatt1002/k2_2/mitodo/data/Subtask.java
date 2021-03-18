package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.Objects;

public class Subtask
{
    private String title;

    public Subtask(String title)
    {
        this.title = title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

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
