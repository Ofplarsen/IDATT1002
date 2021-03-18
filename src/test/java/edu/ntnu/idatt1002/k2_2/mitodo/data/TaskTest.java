package edu.ntnu.idatt1002.k2_2.mitodo.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    Task task = new Task("A task", PriorityEnum.MEDIUM);

    @Nested
    @DisplayName("Tests for addSubtask method")
    class addSubtasksTest{

        @Test
        void subtaskIsSuccessfullyAdded(){
            task.addSubtasks("A subtask");
            Subtask subtask = new Subtask("A subtask");
            Assertions.assertEquals(task.getSubtasks().get(0), subtask);
        }
    }

}
