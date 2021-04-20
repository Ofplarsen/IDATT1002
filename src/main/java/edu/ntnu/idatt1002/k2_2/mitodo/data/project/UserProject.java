package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

import edu.ntnu.idatt1002.k2_2.mitodo.effects.SoundEffects;

public class UserProject extends Project
{
    private String title;
    private final Project parent;

    public UserProject(String title, Project parent)
    {
        if(title.isEmpty() || title.isBlank())
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Title of projects can't be empty");
        }

        if(projectAlreadyCreated(title))
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Project already created");
        }

        this.title = title.trim();
        this.parent = parent;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        if(title.isEmpty() || title.isBlank())
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(projectAlreadyCreated(title))
        {
            SoundEffects.playErrorSound2();
            throw new IllegalArgumentException("Project already created");
        }
        this.title = title;
    }

    public boolean projectAlreadyCreated(String title)
    {
        if(parent == null || parent.getProjects().size() == 0)
        {
            return false;
        }
        return parent.getProjects().stream().anyMatch(p -> p.getTitle().equalsIgnoreCase(title));
    }

    public Project getParent()
    {
        return parent;
    }

    @Override
    public String toString()
    {
        return "\nProject{" +
                "\ntitle='" + title +
                "\n}";
    }
}