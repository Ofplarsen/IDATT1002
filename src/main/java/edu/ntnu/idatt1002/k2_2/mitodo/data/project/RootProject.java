package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

/**
 * A class representing the root project containing all other projects
 * and a list of tasks representing the quick tasks.
 *
 * @version 1.0.0
 */
public class RootProject extends Project
{
    @Override
    public String getTitle()
    {
        return "Quick Tasks";
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
