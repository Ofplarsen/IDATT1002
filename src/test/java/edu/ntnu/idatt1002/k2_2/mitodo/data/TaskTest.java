package edu.ntnu.idatt1002.k2_2.mitodo.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task1 = new Task("Task1");
    Task task2 = new Task("Task2");
    int year = LocalDate.now().getYear();
    Month month = LocalDate.now().getMonth();
    int day = LocalDate.now().getDayOfMonth();

    @BeforeEach
    void initialize(){
        task1.setDates(LocalDate.now(),LocalDate.of(year, month, day+10), RepeatEnum.DoesNotRepeat);
    }

    @Nested
    @DisplayName("Test to make sure all exceptions are handled and that the date system works")
    class dateTests {

        @Nested
        @DisplayName("setStartDate test")
        class setStartDate {
            @Test
            void setStartDateSuccess() {
                task1.setDates(LocalDate.now(), null, RepeatEnum.DoesNotRepeat);
                assertEquals(task1.getStartDate(), LocalDate.now());
            }

            @Test
            void setStartDateAfterDue() {
                try {
                    task1.setDates(LocalDate.of(year, month, day + 11), null, RepeatEnum.DoesNotRepeat);
                } catch (IllegalArgumentException e) {
                    assertEquals(task1.getStartDate(), LocalDate.now());
                    assertEquals(e.getMessage(), "Can't set start date later than due date");
                }
            }


        }

        @Nested
        @DisplayName("Tests for setDue date that catches IllegalArguments")
        class setDueDate {
            @Test
            void setDueDateSuccess() {
                task1.setDates(LocalDate.of(year, month, day + 10), null, RepeatEnum.DoesNotRepeat);
                assertEquals(task1.getDueDate(), LocalDate.of(year, month, day + 10));
            }

            @Test
            void setDueDateBeforeStartDate() {
                try {
                    task1.setDates(LocalDate.of(year, month, day + 1),LocalDate.of(year, month, day), RepeatEnum.DoesNotRepeat);
                } catch (IllegalArgumentException e) {
                    assertEquals(e.getMessage(), "Can't set due date earlier than start date");
                }
            }

            @Test
            void setDueDateBeforeTodaysDate() {
                try {
                    task1.setDates(LocalDate.of(year, month, day + 1), LocalDate.of(year, month, day - 1), RepeatEnum.DoesNotRepeat);
                } catch (IllegalArgumentException e) {
                    assertEquals(e.getMessage(), "Can't set due date earlier than today's date");
                }
            }
        }
        @Nested
        @DisplayName("Tests for setDates methos")
        class setDates{
            @Test
            void setDatesSuccess(){
                task1.setDates(LocalDate.now(),LocalDate.now(), RepeatEnum.DoesNotRepeat);
                assertEquals(task1.getDueDate(), task1.getStartDate());
            }

            @Test
            void setDatesDueDateBeforeStartDate(){
                try{
                    task1.setDates(LocalDate.of(year,month,day+3),LocalDate.of(year,month,day+2), RepeatEnum.DoesNotRepeat);
                }catch (IllegalArgumentException e){
                    assertEquals(e.getMessage(), "Can't set due date earlier than start date");
                }
            }

            @Test
            void setDatesDueDateBeforeLocalDate(){
                try{
                    task1.setDates(LocalDate.of(year,month,day-4),LocalDate.of(year,month,day-2), RepeatEnum.DoesNotRepeat);
                }catch (IllegalArgumentException e){
                    assertEquals(e.getMessage(), "Can't set due date earlier than today's date");
                }
            }
        }
    }


    @Nested
    @DisplayName("Test for UUID")
    class idTest{
        @Test
        void getIdMatchesId(){
            assertEquals(task1.getID(), task1.getID());
        }
        @Test
        void getIdDoesntMatch(){
            assertNotEquals(task1.getID(), task2.getID());
        }
    }

    @Nested
    class equalsTest{
        @Test
        void doesEqualsAll(){
            assertTrue(task1.equals(task1));
        }
        @Test
        void titleSameButNotId(){
            Task task3 = new Task("Task1");
            assertFalse(task1.equals(task3));
        }
        @Test
        void differentTasks(){
            assertFalse(task1.equals(task2));
        }

    }
}