package edu.gorb.musicstudio.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class IntegerNumberValidatorTest {
    @DataProvider(name = "data")
    public Object[][] createDates() {
        return new Object[][]{
                {"2021", true},
                {"-12312", false},
                {"1235-34", false},
                {"345_24_453", false},
                {null, false},
                {"0", true}
        };
    }

    @Test(dataProvider = "data")
    public void isNonNegativeIntegerNumberTest(String number, boolean expected) {
        boolean result = IntegerNumberValidator.isNonNegativeIntegerNumber(number);
        assertEquals(expected, result);
    }
}
