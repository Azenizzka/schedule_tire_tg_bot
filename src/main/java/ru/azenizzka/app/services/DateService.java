package ru.azenizzka.app.services;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import ru.azenizzka.app.exceptions.BellTypeConvertException;
import ru.azenizzka.app.utils.DayUtil;
import ru.azenizzka.app.utils.Day;

public class DateService {
	private static final DateTimeZone zone = DateTimeZone.forID("Asia/Novosibirsk");
	private static DateTime time = new DateTime(zone);

	public static Day getDayOfWeek() throws BellTypeConvertException {
		updateTime();
		return DayUtil.convertNumToDay(time.getDayOfWeek());
	}

	public static int getRawDay() {
		updateTime();
		return time.getDayOfWeek();
	}

	private static void updateTime() {
		time = new DateTime(zone);
	}
}
