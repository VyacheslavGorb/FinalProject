package edu.gorb.musicstudio.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;


public class DateTimeValidatorTest {
    @DataProvider(name = "dates")
    public Object[][] createDates() {
        return new Object[][]{
                {"2021-08-15", true},
                {"2022-08-45", false},
                {"-2021-08--5", false},
                {null, false},
                {"2021-08-19", true}
        };
    }

    @Test(dataProvider = "dates")
    public void isValidDateTest(String date, boolean expected) {
        boolean result = DateTimeValidator.isValidDate(date);
        assertEquals(expected, result);
    }

    @DataProvider(name = "time")
    public Object[][] createTime() {
        return new Object[][]{
                {"10:00", true},
                {"11:00", true},
                {"173:43", false},
                {null, false},
                {"25:30", false}
        };
    }

    @Test(dataProvider = "time")
    public void isValidTimeTest(String time, boolean expected) {
        boolean result = DateTimeValidator.isValidTime(time);
        assertEquals(expected, result);
    }

}
