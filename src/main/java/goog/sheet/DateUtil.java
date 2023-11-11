package goog.sheet;


import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class DateUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static Date getNowDate() {
		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
		Date nowDate = Date.from(zdt.toInstant());
		return nowDate;
	}

	public static Date getToday() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date getDateStamp(Date observedTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(observedTime);
		c.clear(Calendar.HOUR);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);
		return c.getTime();
	}

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate getTodayAsLocalDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static LocalDate getTomorrow() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_MONTH, 1);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}
	
	public static LocalDate getTomorrow(TimeZone timeZone) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.setTimeZone(timeZone);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static LocalDate getNextWeek() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_MONTH, 7);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static LocalDate getLastYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, 300);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static LocalDate getLocalDate(Date date) {
		if (date != null) {
			LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
			return localDate;
		} else {
			return LocalDate.now();
		}
	}

	public static Date getDate(LocalDate localDate) {
		if (localDate != null) {
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			return date;
		} else {
			return new Date(System.currentTimeMillis());
		}
	}
	
	public static Date getDate(LocalDateTime localDateTime) {
		if (localDateTime != null) {
			Date date =  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			return date;
		} else {
			return new Date(System.currentTimeMillis());
		}
	}

	public static String formatDate(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
		inputDate = inputDate != null ? inputDate : getNowDate();
		return format.format(inputDate);

	}

	public static String formatDateTime(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
		inputDate = inputDate != null ? inputDate : getNowDate();
		return format.format(inputDate);

	}

	public static String formatDateTimeFullYear(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		inputDate = inputDate != null ? inputDate : getNowDate();
		return format.format(inputDate);

	}

	public static String formatDateTimeWithSeconds(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		inputDate = inputDate != null ? inputDate : getNowDate();
		return format.format(inputDate);

	}

	public static String formatDateTimeSansSeconds(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
		inputDate = inputDate != null ? inputDate : getNowDate();
		return format.format(inputDate);

	}

	public static String converttoDateFromFbDate(String createdTime) {
		createdTime = createdTime.replaceAll("T", " ");
		createdTime = createdTime.substring(0, createdTime.indexOf("+"));
		// 2019-10-11T06:45:08+0000
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(createdTime);
			return formatDateTimeWithSeconds(date);
		} catch (ParseException e) {
			logger.error("Error translating date, {}", createdTime, e);
		}
		return null;
	}

	public static Date getJavaComplianDate(String createdTime) {
		if (Objects.nonNull(createdTime)) {
			createdTime = createdTime.replaceAll("T", " ");
			if (createdTime.contains("+"))
				createdTime = createdTime.substring(0, createdTime.indexOf("+"));
			else if (createdTime.contains("-"))
				createdTime = createdTime.substring(0, createdTime.lastIndexOf("-"));
			// 2019-10-11T06:45:08+0000
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return format.parse(createdTime);
			} catch (ParseException e) {
				logger.error("Error translating date, {}", createdTime, e);
			}
		}
		return null;
	}

	public static Date getDateTimeFromFBString(String dateTimeString) {
		if (Objects.nonNull(dateTimeString)) {
			dateTimeString = dateTimeString.replaceAll("T", " ");
			if (dateTimeString.contains("+"))
				dateTimeString = dateTimeString.substring(0, dateTimeString.indexOf("+"));
			else if (dateTimeString.contains("-"))
				dateTimeString = dateTimeString.substring(0, dateTimeString.lastIndexOf("-"));
			// 2019-10-11T06:45:08+0000
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = format.parse(dateTimeString);
				return date;
			} catch (ParseException e) {
				logger.error("Error translating date, {}", dateTimeString, e);
			}
		}
		return null;
	}

	public static Date getDateTimeFromFBInsightString(String dateString) {
		// 2019-11-15
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(dateString);
			return date;
		} catch (ParseException e) {
			logger.error("Error translating date, {}", dateString, e);
		}
		return null;
	}

	public static Date getDateTime(String dateString) {
		// 2019-11-15
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(dateString);
			return date;
		} catch (ParseException e) {
			logger.error("Error translating date, {}", dateString, e);
		}
		return null;
	}

	public static Date getDateTime(String dateString, String pattern) {
		// 2019-11-15
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			Date date = format.parse(dateString);
			return date;
		} catch (ParseException e) {
			logger.error("Error translating date, {}", dateString, e);
		}
		return null;
	}

	public static Date getQuarterlyDateStamp(Date observedTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(observedTime);
		int minutePart = c.get(Calendar.MINUTE);
		int multiples = minutePart / 15;
		int reminder = minutePart - (multiples * 15);
		int buffer = 15 - reminder;
		c.add(Calendar.MINUTE, buffer);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);
		return c.getTime();
	}

	public static Date getLastWeek() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, -7);
		return c.getTime();
	}

	public static Date getYesterday() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}

	public static LocalDate getYesterdayAsLocalDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, -1);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static Date getLastFortnight() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, -14);
		return c.getTime();
	}

	public static LocalDate getLastWeekAsLocalDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, -7);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static LocalDate getLastMonthAsLocalDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DAY_OF_YEAR, -30);
		LocalDate localDate = new java.sql.Date(c.getTimeInMillis()).toLocalDate();
		return localDate;
	}

	public static String getCampaignDateTime(String dateTimeString) {
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeString);
			String date2 = new SimpleDateFormat("dd MM yyyy").format(date1);

			return date2;
		} catch (ParseException e) {
			logger.error("Error translating date, {}", dateTimeString, e);
		}
		return null;
	}

	public static String getDateOnlyStringForGoogleCal(LocalDate inputDateLocal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date inputDate = inputDateLocal != null ? getDate(inputDateLocal) : getNowDate();
		return format.format(inputDate);
		
	}
	public static LocalDateTime getDefaultTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		
		c.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		c.add(Calendar.DAY_OF_MONTH, 1);
		
		LocalDateTime localTime = LocalDateTime.now();
		
		return localTime;
		
	
	}
	public static Date getCalandarDateTime(LocalDateTime localDate) {
		Date date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
			return date;
	
	}
	public static String getDateTimeStringForGoogleCal(LocalDateTime inputDateLocal,String timezone,int hour,int minutes, int duration) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");		
		Date inputDate = getDate(inputDateLocal) ;
		Calendar calendar=DateUtils.toCalendar(inputDate);
		calendar.set(Calendar.HOUR_OF_DAY,hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.MINUTE, duration);
		format.setTimeZone(TimeZone.getTimeZone(timezone));
		return format.format(calendar.getTime());
		
	}
	
}