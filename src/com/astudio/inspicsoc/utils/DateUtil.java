package com.astudio.inspicsoc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class DateUtil {

	public final static String TIME_FORMAT_NO_SEPERATE = "yyyyMMdd";
	public final static String TIME_FORMAT_SEPERATE = "yyyy-MM-dd";
	public final static String DATE_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date changeTime(Date date, int hour, int min, int sec) {
		return setCalendarTime(date, hour, min, sec).getTime();
	}

	private static Calendar setCalendarTime(Date date, int hour, int min,
			int sec) {
		Calendar cal = Calendar.getInstance();
		if (null != date) {
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date dateAdd(String interval, int number, Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		if (interval.equals("y")) {
			int currYear = gc.get(Calendar.YEAR);
			gc.set(Calendar.YEAR, currYear + number);
		} else if (interval.equals("m")) {
			int currMonth = gc.get(Calendar.MONTH);
			gc.set(Calendar.MONTH, currMonth + number);
		} else if (interval.equals("d")) {
			int currDay = gc.get(Calendar.DATE);
			gc.set(Calendar.DATE, currDay + number);
		} else if (interval.equals("h")) {
			int currDay = gc.get(Calendar.HOUR);
			gc.set(Calendar.HOUR, currDay + number);
		} else if (interval.equals("minute")) {
			int currDay = gc.get(Calendar.MINUTE);
			gc.set(Calendar.MINUTE, currDay + number);
		}
		return gc.getTime();
	}

	/**
	 * Give a date, like 2012-03-19, will parse it to 2012-03-19 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date beginOfADay(Date date) {
		return setCalendarTime(date, 0, 0, 0).getTime();
	}

	/**
	 * Give a date, like 2012-03-19, will parse it to 2012-03-19 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date endOfADay(Date date) {
		return setCalendarTime(date, 23, 59, 59).getTime();
	}

	public static Date beginOfMonth(Date date) {
		Calendar cal = setCalendarTime(date, 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static Date endOfMonth(Date date) {
		Calendar cal = setCalendarTime(date, 23, 59, 59);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * Calculate the difference of specified field between 2 dates. e.g. begin:
	 * 2012-02-01 10:00:00, end: 2012-02-02 11:00:00
	 * 
	 * If field is Calendar.DAY, then the result is 1. If field is
	 * Calendar.HOUR, then the result is 25.
	 * 
	 * @param begin
	 * @param end
	 * @param field
	 * @see java.util.Calendar
	 * @return
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

	public static Date endDateOfWeek(Date previewDate) {
		Calendar cal = setCalendarTime(previewDate, 0, 0, 0);
		cal.add(Calendar.DAY_OF_MONTH, 7 - cal.get(Calendar.DAY_OF_WEEK));
		return cal.getTime();
	}

	/**
	 * sunday is first day of week
	 * 
	 * @param previewDate
	 * @return
	 */
	public static int weekOfYear(Date previewDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(previewDate);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Data ranges of current month
	 * 
	 * @return
	 */
	public static Date[] currentMonthRanges() {
		Date start = DateUtils.truncate(new Date(), Calendar.MONTH);
		return new Date[] { start,
				DateUtils.addMilliseconds(DateUtils.addMonths(start, 1), -1) };
	}

	/**
	 * Data ranges of previous month
	 * 
	 * @return
	 */
	public static Date[] previousMonthRanges() {
		Date start = DateUtils.truncate(DateUtils.addMonths(new Date(), -1),
				Calendar.MONTH);
		return new Date[] { start,
				DateUtils.addMilliseconds(DateUtils.addMonths(start, 1), -1) };
	}

	/**
	 * Date ranges of current month last year
	 * 
	 * @return
	 */
	public static Date[] currentMonthLastYearRanges() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		return getDates(calendar);
	}

	/**
	 * Date ranges of previous month last year
	 * 
	 * @return
	 */
	public static Date[] previousMonthLastYearRanges() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		calendar.add(Calendar.MONTH, -1);
		return getDates(calendar);
	}

	private static Date[] getDates(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date firstDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDate = calendar.getTime();
		return new Date[] { firstDate, lastDate };
	}

	/**
	 * Translate time by the format.
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrenttimeByFromat(String format) {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat(format);
		String time = df.format(dt);
		return time;
	}

	/**
	 * Get date by the format.
	 * 
	 * @param timestamp
	 *            : for example 1353657455000
	 * @param format
	 * @return
	 */
	public static Date getCurrentDateByFormat(Long timestamp, String format) {
		try {
			Date dt = new Date();
			if (timestamp != null) {
				dt = new Date(timestamp);
			}

			SimpleDateFormat formatter = new SimpleDateFormat(format);
			String dateString = formatter.format(dt);
			return formatter.parse(dateString);

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Translate timestamp into date format
	 * 
	 * @param timestamp
	 *            : for example 1353657455000
	 * @param format
	 * @return
	 */
	public static String formatTimestamp(Long timestamp, String format) {
		Date dt = new Date(timestamp);
		DateFormat df = new SimpleDateFormat(format);
		return df.format(dt);
	}

	/**
	 * format timestamp to String
	 * 
	 * @param durationMillis
	 * @param format
	 * @return
	 */
	public static String formatDuration(long durationMillis, String format) {
		if (StringUtils.isBlank(format)) {
			format = "dd HH:mm:ss";
		}

		if (durationMillis >= 0) {
			return DurationFormatUtils.formatDuration(durationMillis, format);
		} else {
			return "-"
					+ DurationFormatUtils.formatDuration(
							Math.abs(durationMillis), format);
		}
	}

	/**
	 * format Convert
	 * 
	 * @param dateStr
	 * @param format
	 * @param local
	 * @param outFormat
	 * @return
	 */
	public static String formatConvert(String dateStr, String format,
			Locale local, String outFormat) throws ParseException {
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)
				|| StringUtils.isBlank(outFormat)) {
			return dateStr;
		}
		DateFormat indf = null;
		if (null == local) {
			indf = new SimpleDateFormat(format);
		} else {
			indf = new SimpleDateFormat(format, local);
		}
		DateFormat outdf = new SimpleDateFormat(outFormat);
		return outdf.format(indf.parse(dateStr));
	}

	/**
	 * Get millisecond by given 2 days
	 * 
	 * @param from
	 * @param to
	 * @param includeNonBusinessDay
	 * @return
	 */
	public static long getDiffDayMillis(Date from, Date to,
			boolean includeNonBusinessDay) {
		if (null == from || null == to) {
			return 0;
		}

		if (includeNonBusinessDay) {
			return to.getTime() - from.getTime();
		}

		Date truncatedFrom = DateUtils.truncate(from, Calendar.DATE);
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(from);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int month = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (dayOfWeek == Calendar.SATURDAY
				|| dayOfWeek == Calendar.SUNDAY
				|| (month == Calendar.DECEMBER
						&& (25 == dayOfMonth || 31 == dayOfMonth) || (month == Calendar.JANUARY && 1 == dayOfMonth))) {
			truncatedFrom = DateUtils.addDays(truncatedFrom, 1);
			from = truncatedFrom;
		}

		// leaving out holidays "New Year’s Eve  (12.31)",
		// "New Year’s Day (1.1)", "Christmas Day (12.25)"
		int nonBusinessDays = 0;
		while (truncatedFrom.getTime() <= to.getTime()) {
			cal.setTime(truncatedFrom);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			month = cal.get(Calendar.MONTH);
			dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

			if (dayOfWeek == Calendar.SATURDAY
					|| dayOfWeek == Calendar.SUNDAY
					|| (month == Calendar.DECEMBER
							&& (25 == dayOfMonth || 31 == dayOfMonth) || (month == Calendar.JANUARY && 1 == dayOfMonth))) {
				nonBusinessDays++;
			}

			truncatedFrom = DateUtils.addDays(truncatedFrom, 1);
		}

		return to.getTime() - from.getTime() - nonBusinessDays
				* DateUtils.MILLIS_PER_DAY;
	}

	/**
	 * Convert Date from a TimeZone to the other TimeZone
	 * 
	 * @param date
	 * @param fromTimeZone
	 * @param toTimeZone
	 * @return
	 */
	public static Date timeZoneConversion(Date date, TimeZone fromTimeZone,
			TimeZone toTimeZone) {
		if (null == date) {
			return null;
		}

		if (null == toTimeZone) {
			return date;
		}

		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(date);
		if (null != fromTimeZone
				&& !calendar.getTimeZone().equals(fromTimeZone)) {
			calendar.setTimeZone(fromTimeZone);
		}

		// convert date to UTC
		calendar.add(Calendar.MILLISECOND, -calendar.getTimeZone()
				.getRawOffset());

		// convert date from UTC to target timezone
		calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());

		return calendar.getTime();
	}

	/**
	 * Convert Date from a TimeZone to CST
	 * 
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static Date convertToCST(Date date, TimeZone timeZone) {
		return timeZoneConversion(date, timeZone, TimeZone.getTimeZone("CST"));
	}

	/**
	 * Convert Date from a TimeZone to GMT
	 * 
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static Date convertToGMT(Date date, TimeZone timeZone) {
		return timeZoneConversion(date, timeZone, TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Get week from date
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 * @throws Exception
	 */
	public static int dayForWeek(String date, String dateFormat)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(date));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * convert mills to readable string, for example, 10000 will be convert to
	 * 10s. TimeUnit.HOURS.toMillis(28) will be convert to 24h0m0s0ms
	 * 
	 * @param durationMills
	 * @return a string like 1h2m3s40ms
	 */
	public static String convertToReadableString(long durationMills) {
		Long seconds = null;
		Long minutes = null;
		Long hours = null;
		if (durationMills >= 1000) {
			seconds = durationMills / 1000;
			durationMills = durationMills % 1000;
			if (seconds >= 60) {
				minutes = seconds / 60;
				seconds = seconds % 60;
				if (minutes >= 60) {
					hours = minutes / 60;
					minutes = minutes % 60;
				}
			}
		}
		String str = durationMills + "ms";
		if (seconds != null) {
			str = seconds + "s" + str;
		}
		if (minutes != null) {
			str = minutes + "m" + str;
		}
		if (hours != null) {
			str = hours + "h" + str;
		}
		return str;
	}

	/**
	 * get Date by given year, month, dayOfMonth Note: In java if you need to
	 * get May the month should be 4, but in there please input 5 directly
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @return
	 */
	public static Date constructDateByYMD(int year, int month, int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(year, month - 1, dayOfMonth);
		return cal.getTime();
	}

	/**
	 * get yesterday
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * get day before yesterday
	 * 
	 * @return
	 */
	public static Date getDayBeforeYesterDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		return calendar.getTime();
	}

	/**
	 * get day for week
	 * 
	 * @param date
	 * @return
	 */
	public static int dayForWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
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

	/**
	 * without end or begin
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static List<Date> parseDates(Date begin, Date end) {
		List<Date> dateList = new ArrayList<Date>();
		if (begin.after(end)) {
			while (begin.after(end)) {
				Date nextBegin = DateUtils.addDays(begin, -1);
				dateList.add(nextBegin);
				begin = nextBegin;
			}
		} else if (begin.before(end)) {
			while (begin.before(end)) {
				dateList.add(begin);
				begin = DateUtils.addDays(begin, 1);
			}
		} else {
			dateList.add(begin);
		}
		return dateList;
	}

	public static List<Date> parseDatesWithBeginEnd(Date begin, Date end) {
		List<Date> dateList = new ArrayList<Date>();
		if (begin.after(end)) {
			while (!begin.before(end)) {
				Date nextBegin = DateUtils.addDays(begin, -1);
				dateList.add(nextBegin);
				begin = nextBegin;
			}
		} else if (begin.before(end)) {
			while (!begin.after(end)) {
				dateList.add(begin);
				begin = DateUtils.addDays(begin, 1);
			}
		} else {
			dateList.add(begin);
		}
		return dateList;
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

	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		try {
			return formatDate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseRequestDate(String dateStr) throws Exception {
		Date date = null;

		if (StringUtils.isNotBlank(dateStr)) {
			date = parseDateStr(dateStr, "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			DateUtils.setHours(date, calendar.get(Calendar.HOUR));
			DateUtils.setMinutes(date, calendar.get(Calendar.MINUTE));
			DateUtils.setMilliseconds(date, calendar.get(Calendar.MILLISECOND));
		}

		return date;
	}

	public static Date parseRequestDateTime(String dateStr) throws Exception {
		Date date = null;

		if (StringUtils.isNotBlank(dateStr)) {
			date = parseDateStr(dateStr, "yyyy-MM-dd HH:mm");
			Calendar calendar = Calendar.getInstance();
			DateUtils.setHours(date, calendar.get(Calendar.HOUR));
			DateUtils.setMinutes(date, calendar.get(Calendar.MINUTE));
			DateUtils.setMilliseconds(date, calendar.get(Calendar.MILLISECOND));
		}

		return date;
	}

	public static void main(String[] args) throws ParseException {
		String endTimeStr = "20:00:00";
		Date endTime = null;
		Date startTime = new Date();
		try {
			endTime = parseDateStr("2015-03-16 23:00:00", DATE_SEC_FORMAT);
			String today = getTimeByFromat(startTime, TIME_FORMAT_SEPERATE);
			if (endTimeStr == null || endTimeStr == "") {
				endTimeStr = today + " 23:59:59";
			} else {
				endTimeStr = today + " " + endTimeStr;
			}
			endTime = parseDateStr(endTimeStr, DATE_SEC_FORMAT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date currentTime = new Date();
		System.out.println(currentTime + " == " + endTime);
		long hoursDiff = diff(currentTime, endTime, Calendar.HOUR);
		long minutesDiff = diff(currentTime, endTime, Calendar.MINUTE);
		minutesDiff = minutesDiff - hoursDiff * 60;
		System.out.println(hoursDiff + "小时" + minutesDiff + "分");
	}
}
