package com.cab.sccommom.utils.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by by chenanbing on 2018/9/4 11:45.
 */
public class DateTimeUtils {

    public final static int MINUTE = 60;
    public final static int MINUTE3 = MINUTE * 3;
    public final static int MINUTE5 = MINUTE * 5;
    public final static int MINUTE10 = MINUTE * 10;
    public final static int HOUR = MINUTE * 60;
    public final static int HOUR2 = HOUR * 2;
    public final static int HOUR3 = HOUR * 3;
    public final static int HOUR6 = HOUR * 6;
    public final static int HOUR12 = HOUR * 12;
    public final static int DAY = HOUR * 24;
    public final static int WEEK = DAY * 7;
    public final static int TENDAY = DAY * 10;

    public final static String pattern_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String pattern_yyyyMMdd = "yyyy-MM-dd";
    public final static String patterns_yyyyMMdd = "yyyyMMdd";
    public final static String pattern_yy_MM_dd = "yy/MM/dd";
    public final static String pattern_yy_MM = "yyyy-MM";

    public static Long getCurrentSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static Long getSeconds(Date date) {
        return TimeUnit.MILLISECONDS.toSeconds(date.getTime());
    }

    public static Date string2Date(String dateString,String patten) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateString, patten);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Long string2Seconds(String dateString,String patten) {
        Date date = string2Date(dateString,patten);
        return getSeconds(date);
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

    public static String getDateString() {
        return getCurrentString(pattern_yyyyMMdd);
    }

    public static String getYearMonthString() {
        return getCurrentString(pattern_yy_MM);
    }

    /**
     * 获取当前时间的前后时间，num为负数表示以前日期，正数表示未来日期，格式：yyyy-MM-dd
     *
     * @param unit Calendar.YEAR,Calendar.MONTH,Calendar.DATE
     * @param num
     * @return
     */
    public static String getPreDateString(int unit, int num) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(unit, num); //年份减1
        Date lastMonth = ca.getTime(); //结果
        SimpleDateFormat formatter = new SimpleDateFormat(pattern_yyyyMMdd);
        return formatter.format(lastMonth);
    }

    public static void main(String[] args) {
    }
}
