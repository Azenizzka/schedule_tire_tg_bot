package ru.azenizzka.app.utils;

import ru.azenizzka.app.exceptions.BellTypeConvertException;

import java.util.HashMap;
import java.util.Map;

public class DayUtil {
	public static final Map<String, Integer> strIntMap = new HashMap<>(6);
	public static final Map<Integer, Day> intDayMap = new HashMap<>(6);
	public static final Map<Day, String> dayStrMap = new HashMap<>(6);;


	static {
		strIntMap.put("понедельник", 1);
		strIntMap.put("вторник", 2);
		strIntMap.put("среда", 3);
		strIntMap.put("четверг", 4);
		strIntMap.put("пятница", 5);
		strIntMap.put("суббота", 6);

		intDayMap.put(1, Day.MONDAY);
		intDayMap.put(2, Day.TUESDAY);
		intDayMap.put(3, Day.WEDNESDAY);
		intDayMap.put(4, Day.THURSDAY);
		intDayMap.put(5, Day.FRIDAY);
		intDayMap.put(6, Day.SATURDAY);

		dayStrMap.put(Day.MONDAY, "понедельник");
		dayStrMap.put(Day.TUESDAY, "вторник");
		dayStrMap.put(Day.WEDNESDAY, "стреда");
		dayStrMap.put(Day.THURSDAY, "четверг");
		dayStrMap.put(Day.FRIDAY, "пятница");
		dayStrMap.put(Day.SATURDAY, "суббота");
	}

	public static Day convertNumToDay(int num) throws BellTypeConvertException {
		if (intDayMap.containsKey(num)) {
			return intDayMap.get(num);
		} else {
			throw new BellTypeConvertException(MessagesConfig.BELL_TYPE_CONVERT_EXCEPTION);
		}
	}

	public static int convertStrToInt(String str) throws BellTypeConvertException {
		if (strIntMap.containsKey(str)) {
			return strIntMap.get(str);
		} else {
			throw new BellTypeConvertException(MessagesConfig.BELL_TYPE_CONVERT_EXCEPTION);
		}
	}

	public static String convertDayToStr(Day day) throws BellTypeConvertException {
		if (dayStrMap.containsKey(day)) {
			return dayStrMap.get(day);
		} else {
			throw new BellTypeConvertException(MessagesConfig.BELL_TYPE_CONVERT_EXCEPTION);
		}
	}

	public static Day convertStrToDay(String str) throws BellTypeConvertException {
		return convertNumToDay(convertStrToInt(str));
	}
}