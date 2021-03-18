package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class Project
{
    private String title;
    private ArrayList<Subproject> subprojects;

    public Project(String title)
    {
        setTitle(title);

        subprojects = new ArrayList<>();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public Subproject addSubproject(String title, Color color)
    {
        Subproject subproject = new Subproject(title, color);
        subprojects.add(subproject);
        return subproject;
    }

    public void removeSubproject(Subproject subproject)
    {
        subprojects.remove(subproject);
    }

    public void setSubproject(ArrayList<Subproject> subprojects)
    {
        this.subprojects = subprojects;
    }

    public ArrayList<Subproject> getSubprojects()
    {
        return subprojects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return title.equals(project.title);
    }
}
