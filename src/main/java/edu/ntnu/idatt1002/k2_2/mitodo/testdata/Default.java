package edu.ntnu.idatt1002.k2_2.mitodo.testdata;

import edu.ntnu.idatt1002.k2_2.mitodo.data.*;

public class Default
{
    public static void fillWithTestData(Project application)
    {
        application.addProject(new Project("Skole"));
        application.getProjectbyTitle("Skole").addProject(new Project("Matte"));
        application.getProjectbyTitle("Skole").addTask(new Task("Lag struktur"));
        application.getProjectbyTitle("Matte").addTask(new Task("Komplekse tall"));
        application.getProjectbyTitle("Matte").addTask(new Task("Logikk øving 2"));

        application.getProjectbyTitle("Matte").moveTaskbyTitle("Komplekse tall", "Skole", application);
        System.out.println(application.getProjectbyTitle("Skole"));
    }
}
