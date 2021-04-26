package edu.ntnu.idatt1002.k2_2.mitodo.data;

import edu.ntnu.idatt1002.k2_2.mitodo.data.task.PriorityEnum;
import edu.ntnu.idatt1002.k2_2.mitodo.data.task.RepeatEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnumToStringConverterTest {
    EnumToStringConverter converter = new EnumToStringConverter();

    @Nested
    @DisplayName("Tests for making an enum to string")
    public class testToString{
        @Test
        void testToStringPriorityEnum() {

            assertEquals(converter.toString(null), "");
            assertEquals(converter.toString(PriorityEnum.HIGH),"High");
            assertEquals(converter.toString(PriorityEnum.LOW),"Low");
            assertEquals(converter.toString(PriorityEnum.MEDIUM),"Medium");
            assertEquals(converter.toString(PriorityEnum.UNDEFINED),"Undefined");
            assertEquals(converter.toString(PriorityEnum.LOW),"Low");
        }
        @Test
        void testToStringRepeatEnum() {

            assertEquals(converter.toString(null), "");
            assertEquals(converter.toString(RepeatEnum.DOES_NOT_REPEAT),"Does not repeat");
            assertEquals(converter.toString(RepeatEnum.DAILY),"Daily");
            assertEquals(converter.toString(RepeatEnum.WEEKLY),"Weekly");
            assertEquals(converter.toString(RepeatEnum.MONTHLY),"Monthly");
        }
        @Test
        void testToStringFontSize(){
            assertEquals(converter.toString(null), "");
            assertEquals(converter.toString(FontSizeEnum.BIG),"Big");
            assertEquals(converter.toString(FontSizeEnum.MEDIUM),"Medium");
            assertEquals(converter.toString(FontSizeEnum.SMALL),"Small");
        }

    }

}