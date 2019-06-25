package com.cab.sccommom.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;


public class NumberUtils {

	public static DecimalFormat df = new DecimalFormat("0.00");

	public static DecimalFormat df1 = new DecimalFormat("0");

	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormat(Object d){
		if(d==null){
			return "";
		}
		Class<?> type = d.getClass();
		if(type == Double.class||type== Float.class||type == BigDecimal.class){
			return getDefaultFormatHalfDown(d);
		}
		if(type == String.class|| type == Character.class
		   || type == Byte.class || type == Short.class
		   || type == Integer.class || type == Long.class){
			return d.toString();
		}
		return null;
	}

	/**
	 * 默认double数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDown(Double d){
		if(d==null){
			return "";
		}
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}


	/**
	 * 默认double数据格式
	 * @param d
	 * @return
	 */
	public static String getFormat(Double d,String formate){
		if(d==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(formate);
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}

	/**
	 * 默认double数据格式
	 * @param d
	 * @return
	 */
	public static String getFormatHalfUp(Double d,String formate){
		if(d==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(formate);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(d);
	}

	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getFormat(BigDecimal d,String formate){
		if(d==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(formate);
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}

	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDown(BigDecimal d){
		if(d==null){
			return "0.00";
		}
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}

	public static void main(String[] args) {
		System.out.println(getDefaultFormatHalfDownNoDec(new BigDecimal("2000.0000")));
	}

	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDownNoDec(BigDecimal d){
		if(d==null){
			return "0";
		}
		df1.setRoundingMode(RoundingMode.DOWN);
		return df1.format(d);
	}


	/**
	 * 默认BigDecimal数据格式
	 * @param d
	 * @return
	 */
	public static String getDefaultFormatHalfDown(Object d){
		if(d==null){
			return "";
		}
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(d);
	}

	/**
	  * 判断字符串是否是整数
	  */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	 /**
	  * 判断字符串是否是浮点数
	  */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	 }


	 /**
	  * 判断字符串是否是数字
	  */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

  public static String generateKey(){
        return UUID.randomUUID().toString().replaceAll("-","");
   }
}
