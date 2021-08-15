package edu.gorb.musicstudio.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class PageValidatorTest {
    @DataProvider(name = "data")
    public Object[][] createDates() {
        return new Object[][]{
                {"2021", 10, false},
                {"-12312", 10, false},
                {"14", 17, true},
                {"0", 10, false},
                {null, 10, false}
        };
    }

    @Test(dataProvider = "data")
    public void isValidPageParameterTest(String pageParameter, int pageCount, boolean expected) {
        boolean result = PageValidator.isValidPageParameter(pageParameter, pageCount);
        assertEquals(expected, result);
    }
}
