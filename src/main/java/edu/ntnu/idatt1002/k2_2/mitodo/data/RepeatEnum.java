package edu.ntnu.idatt1002.k2_2.mitodo.data;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum RepeatEnum
{
    DoesNotRepeat,
    Daily,
    Weekly,
    Monthly;

    public static StringConverter<RepeatEnum> toString = new StringConverter<RepeatEnum>()
    {
        @Override
        public String toString(RepeatEnum repeatEnum)
        {
            if (repeatEnum == null) return "";
            return repeatEnum.toString().replaceAll("(?!^)([A-Z])", " $1");
        }

        @Override
        public RepeatEnum fromString(String s)
        {
            return null;
        }
    };

    public boolean isShorterThanDates(LocalDate startDate, LocalDate dueDate)
    {
        if(startDate==null || dueDate==null) return false;

        long daysFromStartToDue = startDate.until(dueDate, ChronoUnit.DAYS);

        switch (this)
        {
            case Daily:
                return 1 < daysFromStartToDue;
            case Weekly:
                return 7 < daysFromStartToDue;
            case Monthly:
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
            case Daily:
                return date.plusDays(1);
            case Weekly:
                return date.plusWeeks(1);
            case Monthly:
                return date.plusMonths(1);
            default:
                return date;
        }
    }
}

