package edu.ntnu.idatt1002.k2_2.mitodo.testdata;

import edu.ntnu.idatt1002.k2_2.mitodo.data.*;

import java.time.LocalDate;

public class Default
{
    public static void fillQuickTasksData(Project quickTaskProject){
        quickTaskProject.addProject("Quick Tasks");
        quickTaskProject.addTask("Test bro");
    }
    public static void fillWithTestData(Project project)
    {
        Project skoleProject = project.addProject("Skole");
        skoleProject.addTask("Lag struktur", PriorityEnum.HIGH, null, LocalDate.of(2020,2,3));

        Project matteProject = skoleProject.addProject("Matte");
        matteProject.addTask("Komplekse tall");
        matteProject.addTask("Logikk øving 2");

        Project personligProject = project.addProject("Personlig");
        personligProject.addTask("Kjøp melk");

        Project treningProject = personligProject.addProject("Trening");

        Project intervallProject = treningProject.addProject("Intervall");
        intervallProject.addTask("Jogge eller noe sånn");

        Project styrkeProject = treningProject.addProject("Styrke");
        styrkeProject.addTask("Vektløfting eller noe sånn");
    }
}
