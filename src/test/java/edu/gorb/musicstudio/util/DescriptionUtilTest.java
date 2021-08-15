package edu.gorb.musicstudio.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class DescriptionUtilTest {
    @DataProvider(name = "dates")
    public Object[][] createDates() {
        return new Object[][]{
                {"20210815", "20210815"},
                {"2021081520210815202108152021081202108152021081520210815202108120210815202108152021081520210812021081520210815202108152021081202108152021081520210815202108120210815202108152021081520210812021081520210815202108152021081202108152021081520210815202108111234234234",
                        "2021081520210815202108152021081202108152021081520210815202108120210815202108152021081520210812021081520210815202108152021081202108152021081520210815202108120210815202108152021081520210812021081520210815202108152021081202108152021081520210815202108111..."}
        };
    }

    @Test(dataProvider = "dates")
    public void isValidDateTest(String description, String expected) {
        String result = DescriptionUtil.trimDescriptionForPreview(description);
        assertEquals(expected, result);
    }
}
