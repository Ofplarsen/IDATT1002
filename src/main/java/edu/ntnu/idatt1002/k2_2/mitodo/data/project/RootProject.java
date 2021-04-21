package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

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
