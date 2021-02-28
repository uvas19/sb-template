package com.uvas.common.date.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class DateTimeUtil {

	public static final ZoneId DEFAULT_TIME_ZONE_ID = ZoneId.of("UTC");

	private DateTimeUtil() {
	}

	public static LocalDateTime currentLocalDateTime() {
		return LocalDateTime.now(DEFAULT_TIME_ZONE_ID);
	}

	public static LocalDate currentLocalDate() {
		return LocalDate.now(DEFAULT_TIME_ZONE_ID);
	}

	/**
	 * Returns the service time-zone id.
	 */
	public static TimeZone timeZone() {
		return TimeZone.getTimeZone(DEFAULT_TIME_ZONE_ID);
	}

}
