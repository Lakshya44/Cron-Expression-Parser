package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import CronFields.*;

import Internal.*;

public class CronFieldTest {

    @Test
    public void testPassParseAsterisk() throws InvalidCronFieldException {
        CronFieldType fieldType = new MinutesType();
        CronField cronField = new CronField("*/5", fieldType);
        for(int i=0; i<15; i+=5) {
            assertEquals(cronField.getValues().contains(i*5), true);
        }
    }

    @Test
    public void testFailParseAsterisk() throws InvalidCronFieldException {
        CronFieldType fieldType = new MinutesType();
        CronField cronField = new CronField("*/6", fieldType);
        for(int i=1; i<6; i++) {
            assertEquals(cronField.getValues().contains(i), false);
        }
    }

    @Test
    public void testPassParseHyphenSeperatedValues() throws InvalidCronFieldException {
        CronFieldType fieldType = new MinutesType();
        CronField cronField = new CronField("15-18", fieldType);
        for(int i=15; i<=18; i++) {
            assertEquals(cronField.getValues().contains(i), true);
        }
    }

    @Test
    public void testFailParseHyphenSeperatedValues() throws InvalidCronFieldException {
        CronFieldType fieldType = new MinutesType();
        assertThrows(InvalidCronFieldException.class, () -> {
            new CronField("20-2", fieldType);
        });
    }

    @Test
    public void testPassParseCommaSeperatedValues() throws InvalidCronFieldException {
        CronFieldType fieldType = new MinutesType();
        CronField cronField = new CronField("15,18,3,5,6", fieldType);
        assertEquals(cronField.getValues().contains(15), true);
        assertEquals(cronField.getValues().contains(3), true);
        assertEquals(cronField.getValues().contains(5), true);
        assertEquals(cronField.getValues().contains(6), true);
        assertEquals(cronField.getValues().contains(18), true);
    }

    @Test
    public void testFailParseCommaSeperatedValues() throws InvalidCronFieldException {
        CronFieldType fieldType = new MinutesType();
        assertThrows(InvalidCronFieldException.class, () -> {
            new CronField("15,18,3,5,65", fieldType);
        });
    }
}
