package edu.gorb.musicstudio.validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DateTimeValidator {
    private DateTimeValidator(){
    }

    public static boolean isValidDate(String dateString){
        if(dateString == null){
            return false;
        }
        try{
            LocalDate.parse(dateString);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }

    public static boolean isValidTime(String timeString){
        if(timeString == null){
            return false;
        }
        try{
            LocalTime.parse(timeString);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
