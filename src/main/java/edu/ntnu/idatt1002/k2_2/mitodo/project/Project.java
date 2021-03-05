package edu.ntnu.idatt1002.k2_2.mitodo.project;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Project
{
    private String title;
    private ArrayList<Subproject> subprojects;

    protected Project(String title)
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

    public ArrayList<Subproject> getSubproject()
    {
        return subprojects;
    }
}
