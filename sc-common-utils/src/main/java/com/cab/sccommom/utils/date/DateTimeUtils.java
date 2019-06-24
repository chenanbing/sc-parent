package com.cab.sccommom.utils.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by by chenanbing on 2018/9/4 11:45.
 */
public class DateTimeUtils {

    public final static Locale currentLocate = Locale.TRADITIONAL_CHINESE;
    public final static String pattern_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String pattern_yyyyMMdd = "yyyy-MM-dd";
    public final static String patterns_yyyyMMdd = "yyyyMMdd";
    public final static String pattern_yy_MM_dd = "yy/MM/dd";
    public final static String pattern_yy_MM = "yyyy-MM";



    public static Date string2Time(String dateString) {
        Date date = null;
        try {
            date = DateUtils.parseDateStrictly(dateString, currentLocate, patterns_yyyyMMdd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Long string2Seconds(String dateString,String patten) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateString, patten);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
        return TimeUnit.MILLISECONDS.toSeconds(date.getTime());
    }


    public static Long getCurrentSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }


    public static String ms2String(long second) {
        Date date = new Date(second * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern_yyyyMMddHHmmss);
        return formatter.format(date);
    }

    public static String getPatternTimeString(long second, String pattern) {
        Date date = new Date(second * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern_yyyyMMdd);
        return formatter.format(new Date());
    }

    public static String getYearMonthString() {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern_yy_MM);
        return formatter.format(new Date());
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

    public static String formatDateByRegex(Date date, String regex) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(regex);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String formatDate() {
        return formatDateByRegex(new Date(),pattern_yy_MM_dd);
    }


    public static void main(String[] args) {
    	BitSet a = new BitSet(100);
    	for (int i = 1; i < 101; i++) {
			a.set(i);
		}
    	BitSet b = new BitSet(100);
    	for (int i = 1; i < 51; i++) {
    		b.set(i);
		}
    	a.andNot(b);
    	for (int i = 0; i < a.size(); i++) {
    		if(a.get(i)) {
    			System.err.println(i);
    		}
		}
        System.err.println(a.cardinality());
    }
}
