package edu.gorb.musicstudio.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private DateValidator(){
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
}
