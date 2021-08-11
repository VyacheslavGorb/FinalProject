package edu.gorb.musicstudio.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
    private DateUtil(){
    }

    public static List<LocalDate> generateDateRage(LocalDate start, LocalDate end){
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = start;
        while (!date.isAfter(end)) {
            dates.add(date);
            date = date.plusDays(1);
        }
        return dates;
    }
}
