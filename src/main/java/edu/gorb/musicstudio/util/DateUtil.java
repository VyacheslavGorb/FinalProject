package edu.gorb.musicstudio.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
    private DateUtil(){
    }

    /**
     * Generates list of dates between <code>start</code> and <code>end</code> inclusive
     * @param start start date
     * @param end end date
     * @return list of {@link LocalDate} representing range
     */
    public static List<LocalDate> generateDateRange(LocalDate start, LocalDate end){
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = start;
        while (!date.isAfter(end)) {
            dates.add(date);
            date = date.plusDays(1);
        }
        return dates;
    }
}
