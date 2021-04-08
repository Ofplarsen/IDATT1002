package edu.ntnu.idatt1002.k2_2.mitodo.testdata;

import edu.ntnu.idatt1002.k2_2.mitodo.data.*;

import java.util.Date;

public class Default
{
    public static void fillWithTestData(Project application)
    {
        application.addProject(new Project("Skole"));
        application.getProjectbyTitle("Skole").addProject(new Project("Matte"));
        application.getProjectbyTitle("Skole").addTask(new Task("Lag struktur"));
        application.getProjectbyTitle("Matte").addTask(new Task("Komplekse tall"));
        application.getProjectbyTitle("Matte").addTask(new Task("Logikk øving 2"));

        application.addProject(new Project("Personlig"));
        application.getProjectbyTitle("Personlig").addProject(new Project("Trening"));
        application.getProjectbyTitle("Trening").addProject(new Project("Intervall"));
        application.getProjectbyTitle("Trening").addProject(new Project("Styrke"));

        application.getProjectbyTitle("Personlig").addTask(new Task("Kjøp melk"));
        application.getProjectbyTitle("Intervall").addTask(new Task("Jogge eller noe sånn"));
        application.getProjectbyTitle("Intervall").addTask(new Task("Jepp"));
        application.getProjectbyTitle("Intervall").addTask(new Task("Halo"));
        application.getProjectbyTitle("Styrke").addTask(new Task("Vektløfting eller noe sånn"));

        //From 17-24 is to see if Jackson is capable of saving all the variables
        Task taskForJsonTest = new Task("JSON osv");
        taskForJsonTest.setStartDate(new Date(1999, 10, 20));
        taskForJsonTest.setPriority(PriorityEnum.MEDIUM);
        Task taskForJsonTest2 = new Task("JSON 2 osv");
        taskForJsonTest2.setDueDate(new Date());
        application.getProjectbyTitle("Skole").addTask(taskForJsonTest);
        application.getProjectbyTitle("Skole").addTask(taskForJsonTest2);

        application.getProjectbyTitle("Matte").moveTaskbyTitle("Komplekse tall", "Skole", application);
        System.out.println(application.getProjectbyTitle("Skole"));
    }
}
