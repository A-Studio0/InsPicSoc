package com.astudio.inspicsoc.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

/**
 * Created by zm on 3/16/2015.
 */
public class DateUtil {

	public final static String DATE_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String TIME_FORMAT_SEPERATE = "yyyy-MM-dd";
	public final static String CN_TIME_FORMAT_SEPERATE = "yyyy年MM月dd日";

	/**
	 *
	 * @param startTime
	 * @param endTimeStr
	 * @return Map<String, Integer> key1: diffHours; key2: diffMinutes
	 */
	public static Map<String, Integer> getDifferentHoursAndMinutes(
			Date startTime, String endTimeStr) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		Date endTime = null;
		if (startTime == null) {
			resultMap.put("diffHours", 0);
			resultMap.put("diffMinutes", 0);
			resultMap.put("diffSeconds", 0);

			return resultMap;
		}
		try {
			String today = getTimeByFromat(startTime, TIME_FORMAT_SEPERATE);
			if (endTimeStr == null || endTimeStr == "") {
				endTimeStr = today + " 23:59:59";
			} else {
				endTimeStr = today + " " + endTimeStr;
			}
			Log.i("startTime:" + startTime + ", endTimeStr:", endTimeStr);
			endTime = parseDateStr(endTimeStr, DATE_SEC_FORMAT);
		} catch (Exception e) {
			Log.e("日期解析出错", e.getMessage());
		}
		long hoursDiff = diff(startTime, endTime, Calendar.HOUR);
		long minutesDiff = diff(startTime, endTime, Calendar.MINUTE);
		long diffSeconds = diff(startTime, endTime, Calendar.SECOND);
		minutesDiff = minutesDiff - hoursDiff * 60;
		diffSeconds = diffSeconds - (hoursDiff * 60 * 60);
		resultMap.put("diffHours", Integer.parseInt(hoursDiff + ""));
		resultMap.put("diffMinutes", Integer.parseInt(minutesDiff + ""));
		resultMap.put("diffSeconds", Integer.parseInt(diffSeconds + ""));

		return resultMap;
	}

	/**
	 * Calculate the difference of specified field between 2 dates. e.g. begin:
	 * 2012-02-01 10:00:00, end: 2012-02-02 11:00:00
	 * <p/>
	 * If field is Calendar.DAY, then the result is 1. If field is
	 * Calendar.HOUR, then the result is 25.
	 *
	 * @param begin
	 * @param end
	 * @param field
	 * @return
	 * @see java.util.Calendar
	 */
	public static long diff(Date begin, Date end, int field) {
		long diff = end.getTime() - begin.getTime();
		if (Calendar.MILLISECOND == field) {
			return diff;
		}
		diff /= 1000;
		if (Calendar.SECOND == field) {
			return diff;
		}
		diff /= 60;
		if (Calendar.MINUTE == field) {
			return diff;
		}
		diff /= 60;
		if (Calendar.HOUR == field || Calendar.HOUR_OF_DAY == field) {
			return diff;
		}
		diff /= 24;
		if (Calendar.DAY_OF_MONTH == field || Calendar.DAY_OF_WEEK == field
				|| Calendar.DAY_OF_WEEK_IN_MONTH == field
				|| Calendar.DAY_OF_YEAR == field) {
			return diff;
		}
		if (Calendar.MONTH == field) {
			return diff / 30;
		}
		if (Calendar.YEAR == field) {
			return diff / 365;
		}

		return diff;
	}

	/**
	 * parse date string to Date object.
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date parseDateStr(String dateStr, String format) {
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		try {
			return formatDate.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Translate time by the format.
	 *
	 * @param format
	 * @return
	 */
	public static String getTimeByFromat(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		String time = df.format(date);
		return time;
	}

	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);

		return c.getTime();
	}
}
