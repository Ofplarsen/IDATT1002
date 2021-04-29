package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * An enum representing the different repeating frequencies a task can have.
 *
 * @version 1.0.0
 */
public enum RepeatEnum implements Serializable
{
    DOES_NOT_REPEAT,
    DAILY,
    WEEKLY,
    MONTHLY;

    /**
     * Checks if the repeating period is shorter than the time between two dates.
     * @param startDate The start date.
     * @param dueDate The due date.
     * @return True if the repeating period is shorter than the time between two dates. Otherwise false.
     */
    public boolean isShorterThanDates(LocalDate startDate, LocalDate dueDate)
    {
        if(startDate==null || dueDate==null) return false;

        long daysFromStartToDue = startDate.until(dueDate, ChronoUnit.DAYS);

        switch (this)
        {
            case DAILY:
                return 1 < daysFromStartToDue;
            case WEEKLY:
                return 7 < daysFromStartToDue;
            case MONTHLY:
                long monthsFromStartToDue = startDate.until(dueDate.minusDays(1), ChronoUnit.MONTHS);
                return monthsFromStartToDue > 0;
            default:
                return false;
        }
    }

    /**
     * @param prevDate The previous date.
     * @return The previous date + the repeating period.
     */
    public LocalDate getNextDate(LocalDate prevDate)
    {
        if (prevDate==null) return null;

        switch (this)
        {
            case DAILY:
                return prevDate.plusDays(1);
            case WEEKLY:
                return prevDate.plusWeeks(1);
            case MONTHLY:
                return prevDate.plusMonths(1);
            default:
                return prevDate;
        }
    }
}

