package com.solutions.datamart.util;

import java.util.Calendar;
import java.util.Date;

public class DataMartUtil {

    public static boolean same(Date fromDate, Date toDate) {
        return fromDate.equals(toDate);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
