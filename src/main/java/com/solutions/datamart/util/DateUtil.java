package com.solutions.datamart.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;

@Slf4j
public class DateUtil {

    public static Date convertToDate(String inputDate) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(inputDate);
        } catch (ParseException e) {
            log.error(EXCEPTION_MESSAGE, "parsing date ", "convertToDate", e.getMessage(), e.getCause());
        }
        return date;
    }
}
