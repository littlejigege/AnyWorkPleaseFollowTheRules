package com.qgstudio.anywork.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yason 2017/8/13.
 */

public class DateUtil {

    private DateUtil() {
    }

    public static String dateToString(Date data, String pattern) {
        return new SimpleDateFormat(pattern).format(data);
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static Date stringToDate(String text, String pattern)
            throws ParseException {
        return new SimpleDateFormat(pattern).parse(text);
    }

    public static Date longToDate(long millSec) {
        return new Date(millSec);
    }

    public static String longToString(long millSec, String pattern) {
        Date date = longToDate(millSec);
        String text = dateToString(date, pattern);
        return text;
    }

    public static long stringToLong(String text, String pattern)
            throws ParseException {
        Date date = stringToDate(text, pattern);
        long millSec = dateToLong(date);
        return millSec;
    }

}
