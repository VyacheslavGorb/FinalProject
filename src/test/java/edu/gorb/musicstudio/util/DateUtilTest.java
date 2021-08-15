package edu.gorb.musicstudio.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class DateUtilTest {
    @DataProvider(name = "dates")
    public Object[][] createDates() {
        return new Object[][]{
                {LocalDate.of(2021, 11, 1), LocalDate.of(2021, 11, 3),
                        List.of(LocalDate.of(2021, 11, 1),
                                LocalDate.of(2021, 11, 2),
                                LocalDate.of(2021, 11, 3)),
                },
                {
                        LocalDate.of(2021, 11, 1), LocalDate.of(2021, 10, 3),
                        new ArrayList<>()
                }
        };
    }

    @Test(dataProvider = "dates")
    public void isValidDateTest(LocalDate start, LocalDate end, List<LocalDate> expected) {
        List<LocalDate> result = DateUtil.generateDateRange(start, end);
        assertEquals(expected, result);
    }
}
