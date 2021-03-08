package edu.ntnu.idatt1002.k2_2.mitodo.testdata;

import edu.ntnu.idatt1002.k2_2.mitodo.project.*;
import javafx.scene.paint.Color;

public class TestData
{
    public static void fillWithTestData()
    {
        ProjectManager.getQuickTasks().addTask("Buy milk", PriorityEnum.HIGH);
        ProjectManager.getQuickTasks().addTask("Do this", PriorityEnum.HIGH);
        ProjectManager.getQuickTasks().addTask("Do that", PriorityEnum.HIGH);

        Project projectNTNU = ProjectManager.addProject("NTNU");
        Subproject subprojectNTNUMatte = projectNTNU.addSubproject("Matematiske metoder", Color.LIGHTBLUE);
        Subproject subprojectNTNUSystem = projectNTNU.addSubproject("Systemutvikling", Color.LIGHTYELLOW);
        Subproject subprojectNTNUProgram = projectNTNU.addSubproject("Programmering", Color.LIGHTPINK);

        Project projectPersonal = ProjectManager.addProject("Personal");
        Subproject subprojectPersonalWorkOut = projectPersonal.addSubproject("Work out plan", Color.LIGHTSEAGREEN);
    }
}
