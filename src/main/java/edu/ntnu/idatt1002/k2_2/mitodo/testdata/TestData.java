package edu.ntnu.idatt1002.k2_2.mitodo.testdata;

import edu.ntnu.idatt1002.k2_2.mitodo.controller.DataManager;
import edu.ntnu.idatt1002.k2_2.mitodo.data.*;
import javafx.scene.paint.Color;

public class TestData
{
    public static void fillWithTestData()
    {
        DataManager.getQuickTasks().addTask("Buy milk", PriorityEnum.HIGH);
        DataManager.getQuickTasks().addTask("Do this", PriorityEnum.HIGH);
        DataManager.getQuickTasks().addTask("Do that", PriorityEnum.HIGH);

        Project projectNTNU = DataManager.addProject("NTNU");
        Subproject subprojectNTNUMatte = projectNTNU.addSubproject("Matematiske metoder", Color.LIGHTBLUE);
        Subproject subprojectNTNUSystem = projectNTNU.addSubproject("Systemutvikling", Color.LIGHTYELLOW);
        Subproject subprojectNTNUProgram = projectNTNU.addSubproject("Programmering", Color.LIGHTPINK);

        Project projectPersonal = DataManager.addProject("Personal");
        Subproject subprojectPersonalWorkOut = projectPersonal.addSubproject("Work out plan", Color.LIGHTSEAGREEN);
    }
}
