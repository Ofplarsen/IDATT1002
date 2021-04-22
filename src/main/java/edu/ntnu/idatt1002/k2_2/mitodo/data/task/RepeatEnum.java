package edu.ntnu.idatt1002.k2_2.mitodo.data.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum RepeatEnum implements Serializable
{
    DOES_NOT_REPEAT,
    DAILY,
    WEEKLY,
    MONTHLY;

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

    public LocalDate getNextDate(LocalDate date)
    {
        if (date==null) return null;

        switch (this)
        {
            case DAILY:
                return date.plusDays(1);
            case WEEKLY:
                return date.plusWeeks(1);
            case MONTHLY:
                return date.plusMonths(1);
            default:
                return date;
        }
    }
}

