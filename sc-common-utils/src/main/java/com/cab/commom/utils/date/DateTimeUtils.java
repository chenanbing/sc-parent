package com.cab.commom.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by by chenanbing on 2018/9/4 11:45.
 */
public class DateTimeUtils {
    public static final String PATTERN_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_yyyyMMdd = "yyyy-MM-dd";
    public static final String PATTERN_yyyyMM = "yyyy-MM";

    public static Long getCurrentSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static Long getSeconds(Date date) {
        return TimeUnit.MILLISECONDS.toSeconds(date.getTime());
    }

    public static Long getSeconds(String dateString,String patten) {
        Date date = string2Date(dateString,patten);
        return getSeconds(date);
    }

    public static Date string2Date(String dateString,String patten) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(patten);
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date seconds2Date(long second) {
        Date date = new Date(second * 1000);
        return date;
    }

    public static String seconds2String(long second,String patten) {
        Date date = seconds2Date(second);
        SimpleDateFormat formatter = new SimpleDateFormat(patten);
        return formatter.format(date);
    }

    public static String getCurrentString(String patten) {
        SimpleDateFormat formatter = new SimpleDateFormat(patten);
        return formatter.format(new Date());
    }

    /**
     * 获取当前时间的前后时间，num为负数表示以前日期，正数表示未来日期，格式：yyyy-MM-dd
     *
     * @param unit Calendar.YEAR,Calendar.MONTH,Calendar.DATE
     * @param num
     * @return
     */
    public static Date getPreDateString(int unit, int num) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(unit, num); //年份减1
        Date date = ca.getTime(); //结果
        return date;
    }

    public static void main(String[] args) {
    }
}
