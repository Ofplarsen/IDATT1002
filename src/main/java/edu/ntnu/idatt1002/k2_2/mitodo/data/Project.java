package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for project with methods to edit subprojects.
 */
public class Project
{
    private String title;
    private ArrayList<Subproject> subprojects;

    /**
     * Constructs a new Project.
     * @param title The title of the project.
     */
    public Project(String title)
    {
        this.title = title;

        subprojects = new ArrayList<>();
    }

    /**
     * Sets the project title.
     * @param title The project title.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return The project title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Adds a new Subproject to this project.
     * @param title The title of the subproject.
     * @param color The color of the subproject.
     * @return The created subproject.
     */
    public Subproject addSubproject(String title, Color color)
    {
        Subproject subproject = new Subproject(title, color);
        subprojects.add(subproject);
        return subproject;
    }

    /**
     * Removes a subproject from this project.
     * @param subproject The subproject to remove.
     * @return true if this project contained the subproject.
     */
    public boolean removeSubproject(Subproject subproject)
    {
        return subprojects.remove(subproject);
    }

    /**
     * @return The subprojects in this project.
     */
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
