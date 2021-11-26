package fr.commentary.adventcalendar.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
    public static String getTodayDate() {
        java.util.Date today = new java.util.Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(today);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(today).toString();

        return str;
    }

    public static String getTodayDay() {
        java.util.Date today = new java.util.Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(today);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(today);

        String[] arrayOfString = dateStr.split("-");

        return arrayOfString[2];
    }

    public static String getTodayMonth() {
        java.util.Date today = new java.util.Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(today);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(today);
        String[] arrayOfString = dateStr.split("-");

        return arrayOfString[1];
    }
}
