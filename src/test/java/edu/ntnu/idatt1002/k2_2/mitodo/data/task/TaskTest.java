package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import edu.ntnu.idatt1002.k2_2.mitodo.data.project.RootProject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    RootProject rootProject = new RootProject();
    int year = LocalDate.now().getYear();
    int month = LocalDate.now().getMonthValue();
    int day = LocalDate.now().getDayOfMonth();
    @Nested
    @DisplayName("Tests to make sure creating an instance of Task works and throws like we want")
    public class CreateTask{

        @Nested
        @DisplayName("Tests for Task(String name, Project parent) constructor")
        public class TaskConstructor1{
            @Test
            void emptyTitleThrows(){
                assertThrows(IllegalArgumentException.class, () ->{
                   Task taskTest = new Task("", rootProject);
                });
            }
            @Test
            void emptyTitleThrowsMessage(){
                try{
                    Task taskTest = new Task("", rootProject);
                }catch (IllegalArgumentException e){
                    assertEquals(e.getMessage(), "Empty String is not accepted as title");
                }
            }

            @Test
            void titleLongerThan28Throws(){
                assertThrows(IllegalArgumentException.class, () ->{
                    Task taskTest = new Task("12345678901234567890123456789", rootProject);
                });
            }

            @Test
            void titleLongerThan28ThrowsMessage(){
                try{
                    Task taskTest = new Task("12345678901234567890123456789", rootProject);
                }catch (IllegalArgumentException e){
                    assertEquals(e.getMessage(), "Task title must be below 28 characters.");
                }
            }

        }

        @Nested
        @DisplayName("Tests for creating a task using the second constructor (all info in constructor)")
        public class TaskConstructor2{
            @Nested
            @DisplayName("Tests for title eceptions")
            public class TitleTests {
                @Test
                void emptyTitleThrows() {
                    assertThrows(IllegalArgumentException.class, () -> {
                        Task taskTest = new Task("", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "", rootProject);
                    });
                }

                @Test
                void emptyTitleThrowsMessage() {
                    try {
                        Task taskTest = new Task("", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "", rootProject);
                    } catch (IllegalArgumentException e) {
                        assertEquals(e.getMessage(), "Empty String is not accepted as title");
                    }
                }

                @Test
                void titleLongerThan28Throws() {
                    assertThrows(IllegalArgumentException.class, () -> {

                        Task taskTest = new Task("12345678901234567890123456789", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "", rootProject);
                    });
                }

                @Test
                void titleLongerThan28ThrowsMessage() {
                    try {
                        Task taskTest = new Task("12345678901234567890123456789", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "", rootProject);
                    } catch (IllegalArgumentException e) {
                        assertEquals(e.getMessage(), "Task title must be below 28 characters.");
                    }
                }

                @Test
                void titleTrim(){
                    Task taskTest = new Task("  task   ", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "", rootProject);
                    assertEquals("task", taskTest.getTitle());
                }
            }

            @Test
            void whenPriorityEnumIsNull(){
                Task taskTest = new Task("task", null, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, "", rootProject);
                assertEquals(PriorityEnum.UNDEFINED, taskTest.getPriority());
            }

            @Test
            void commentsTrim(){
                Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT,"  aa   ", rootProject);
                assertEquals(taskTest.getComments(), "aa");
            }

            @Test
            void commentsTrimWhenNull(){
                Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT, null, rootProject);
                assertNull(taskTest.getComments());
            }

            @Test
            void repeatEnumNull(){
                Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), null, "", rootProject);
                assertEquals(taskTest.getRepeat(), RepeatEnum.DOES_NOT_REPEAT);
            }

            @Nested
            @DisplayName("Tests for setting the dates from constuctor")
            public class SettingDates{
                Task taskTest = new Task("Task", rootProject);
                @Test
                void setDatesSuccessful(){
                    Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.now(), LocalDate.now(), RepeatEnum.WEEKLY, "", rootProject);
                }

                @Test
                void dueDateBeforeStart(){
                    assertThrows(IllegalArgumentException.class, () ->{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day+1), LocalDate.now(), null, "", rootProject);
                    });
                    try{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day+1), LocalDate.now(), null, "", rootProject);
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Can't set due date earlier than start date");
                    }
                }

                @Test
                void dueDateBeforeToday(){
                    assertThrows(IllegalArgumentException.class, () ->{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day-4), LocalDate.of(year,month,day-1), null, "", rootProject);
                    });
                    try{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day-4), LocalDate.of(year,month,day-1), null, "", rootProject);
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Can't set due date earlier than today's date");
                    }
                }

                @Test
                void  repeatWithoutStartOrDueDate(){
                    assertThrows(IllegalArgumentException.class, () ->{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, null, null, RepeatEnum.MONTHLY, "", rootProject);
                    });
                    try{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, null, null, RepeatEnum.MONTHLY, "", rootProject);
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Can't repeat without either start date or due date.");
                    }
                }

                @Test
                void repeatIsShorterThanDatesDaily(){
                    assertThrows(IllegalArgumentException.class, () ->{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day), LocalDate.of(year,month,day+2), RepeatEnum.DAILY, "", rootProject);
                    });
                    try{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day), LocalDate.of(year,month,day+2), RepeatEnum.DAILY, "", rootProject);
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Time between start date and due date can't be longer than the repeating period.");
                    }
                }

                @Test
                void repeatIsShorterThanDatesWeekly(){
                    assertThrows(IllegalArgumentException.class, () ->{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day+2), LocalDate.of(year,month+1,day), RepeatEnum.WEEKLY, "", rootProject);
                    });
                    try{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day+2), LocalDate.of(year,month+1,day), RepeatEnum.WEEKLY, "", rootProject);
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Time between start date and due date can't be longer than the repeating period.");
                    }
                }

                @Test
                void repeatIsShorterThanDatesMonthly(){
                    assertThrows(IllegalArgumentException.class, () ->{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day), LocalDate.of(year,month+2,day), RepeatEnum.MONTHLY, "", rootProject);
                    });
                    try{
                        Task taskTest = new Task("task", PriorityEnum.HIGH, LocalDate.of(year,month,day), LocalDate.of(year,month+2,day), RepeatEnum.MONTHLY, "", rootProject);
                    }catch (IllegalArgumentException e){
                        assertEquals(e.getMessage(), "Time between start date and due date can't be longer than the repeating period.");
                    }
                }
            }

        }
    }

    @Nested
    @DisplayName("Tests for setTitle")
    public class SetTitle{
        Task testTask = new Task("Task", rootProject);
        @Test
        void setTitleSuccess() {
            testTask.setTitle("new title");
            assertEquals(testTask.getTitle(), "new title");
        }
        @Test
        void setTitleEmpty(){
            assertThrows(IllegalArgumentException.class, ()->{
               testTask.setTitle("");
            });
            try{
                testTask.setTitle("");
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Empty String is not accepted as title");
            }
        }
        @Test
        void setTitleTrim(){
            testTask.setTitle("  a   ");
            assertEquals("a", testTask.getTitle());
        }

        @Test
        void setTitleLongerThan28(){
            assertThrows(IllegalArgumentException.class, ()->{
                testTask.setTitle("12345678901234567890123456789");
            });
            try{
                testTask.setTitle("12345678901234567890123456789");
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Task title must be below 28 characters.");
            }
        }
    }

    @Test
    void getParent() {
        Task testTask = new Task("Test", rootProject);
        assertEquals(rootProject, testTask.getParent());
    }

    @Nested
    @DisplayName("Tests for setDates method with throws")
    public class SetDates{
        Task taskTest = new Task("Task", rootProject);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        @Test
        void setDatesSuccessful(){
            taskTest.setDates(LocalDate.now(), LocalDate.now(), RepeatEnum.DOES_NOT_REPEAT);
        }

        @Test
        void dueDateBeforeStart(){
            assertThrows(IllegalArgumentException.class, () ->{
                taskTest.setDates(LocalDate.of(year,month,day).plusDays(1), LocalDate.now(), null);
            });
            try{
                taskTest.setDates(LocalDate.of(year,month,day).plusDays(1), LocalDate.now(), null);
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Can't set due date earlier than start date");
            }
        }

        @Test
        void dueDateBeforeToday(){
            assertThrows(IllegalArgumentException.class, () ->{
                taskTest.setDates(LocalDate.of(year,month,day).minusDays(4), LocalDate.of(year,month,day).minusDays(1), null);
            });
            try{
                taskTest.setDates(LocalDate.of(year,month,day).minusDays(4), LocalDate.of(year,month,day).minusDays(1), null);
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Can't set due date earlier than today's date");
            }
        }

        @Test
        void  repeatWithoutStartOrDueDate(){
            assertThrows(IllegalArgumentException.class, () ->{
                taskTest.setDates( null, null, RepeatEnum.MONTHLY);
            });
            try{
                taskTest.setDates( null, null, RepeatEnum.MONTHLY);
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Can't repeat without either start date or due date.");
            }
        }

        @Test
        void repeatIsShorterThanDatesDaily(){
            assertThrows(IllegalArgumentException.class, () ->{
                taskTest.setDates(LocalDate.of(year,month,day), LocalDate.of(year,month,day).plusDays(2), RepeatEnum.DAILY);
            });
            try{
                taskTest.setDates(LocalDate.of(year,month,day), LocalDate.of(year,month,day).plusDays(2), RepeatEnum.DAILY);
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Time between start date and due date can't be longer than the repeating period.");
            }
        }

        @Test
        void repeatIsShorterThanDatesWeekly(){
            assertThrows(IllegalArgumentException.class, () ->{
                taskTest.setDates(LocalDate.of(year,month,day).plusDays(2), LocalDate.of(year,month,day).plusMonths(1), RepeatEnum.WEEKLY);
            });
            try{
                taskTest.setDates(LocalDate.of(year,month,day).plusDays(2), LocalDate.of(year,month,day).plusMonths(1), RepeatEnum.WEEKLY);
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Time between start date and due date can't be longer than the repeating period.");
            }
        }

        @Test
        void repeatIsShorterThanDatesMonthly(){
            assertThrows(IllegalArgumentException.class, () ->{
                taskTest.setDates(LocalDate.of(year,month,day), LocalDate.of(year,month,day).plusMonths(2), RepeatEnum.MONTHLY);
            });
            try{
                taskTest.setDates(LocalDate.of(year,month,day), LocalDate.of(year,month,day).plusMonths(2), RepeatEnum.MONTHLY);
            }catch (IllegalArgumentException e){
                assertEquals(e.getMessage(), "Time between start date and due date can't be longer than the repeating period.");
            }
        }
    }

    @Test
    void deleteItself() {
        Task testTask = rootProject.addTask("TestTask");
        assertTrue(rootProject.getTasks().stream().anyMatch(t->t.getID()==testTask.getID()));
        testTask.deleteItself();
        assertFalse(rootProject.getTasks().stream().anyMatch(t->t.getID()==testTask.getID()));
    }

}