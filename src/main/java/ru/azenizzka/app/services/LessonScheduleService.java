package ru.azenizzka.app.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.azenizzka.app.utils.Day;
import ru.azenizzka.app.utils.DayUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class LessonScheduleService {
	private String url;
	private Elements rows;

	private int groupColumn;
	private int neededRow = 0;

	public List<List<String>> getLessons(int groupNum, Day day) throws Exception {
		initUrl(groupNum);
		initNeededRow();
		initGroupColumn(groupNum);
		List<List<String>> lessonsList = new ArrayList<>();

		int dayNum;

		if (day == Day.TODAY) {
			dayNum = DateService.getRawDay() - 1;
		} else {
			dayNum = DayUtil.convertDayToInt(day) - 1;
		}


		switch (day) {
			case TODAY -> dayNum = (DateService.getRawDay() - 1);
			case MONDAY -> dayNum = 0;
			case TUESDAY -> dayNum = 1;
			case WEDNESDAY -> dayNum = 2;
			case THURSDAY -> dayNum = 3;
			case FRIDAY -> dayNum = 4;
			case SATURDAY -> dayNum = 5;
		}

		int increment = 3;

		int startRowIndex = (neededRow + increment) + 6 * dayNum;
		int endRowIndex = startRowIndex + 6;
		int need = getGroupCount() * 3;


		for (int rowIndex = startRowIndex; rowIndex < endRowIndex; rowIndex++) {
			Element row = rows.get(rowIndex);
			Elements columns = row.select("td");

			increment = 0;
			while (initStartColumnIndex(columns) == -1) {
				increment++;

				startRowIndex = (neededRow + increment) + (6 * dayNum);
				rowIndex = startRowIndex;

				endRowIndex = startRowIndex + 6;
				row = rows.get(rowIndex);
				columns = row.select("td");

			}

			int index = initStartColumnIndex(columns);

			int decrement = 0;

			for (int i = columns.size(); i > (need + index); i--) {
				if (columns.get(i - 1).text().isEmpty()) {
					decrement++;
				} else {
					break;
				}
			}


			if ((columns.size() - decrement - index) % 3 != 0) {
				throw new Exception("В строке недостаточно столбцов! Может быть вызвано из-за ВПР");
			}

			String lesson = columns.get(index + 1 + (groupColumn * 3)).text();
			String num = columns.get(index + (groupColumn * 3)).text();

			String cabinet = columns.get(index + 2 + (groupColumn * 3)).text();

			if (!lesson.isEmpty()) {
				List<String> tempList = new ArrayList<>();
				tempList.add(num);
				tempList.add(lesson);
				tempList.add(cabinet);

				lessonsList.add(tempList);
			}
		}
		return lessonsList;
	}

	private int initStartColumnIndex(Elements columns) {
		for (int i = 0; i < columns.size(); i++) {
			Pattern pattern = Pattern.compile("^\\d+");
			if (pattern.matcher(columns.get(i).text()).matches()) {
				return i;
			}
		}

		return -1;
	}

	private void initNeededRow() {
		Element row;

		for (int i = 0; i < 10; i++) { // Нахождение первой строки, где более трех заполненных колонок
			row = rows.get(i);

			int counter = 0;
			for (Element column : row.select("td")) {
				if (!column.text().isEmpty()) {
					counter++;
				}
			}

			if (counter > 3) {
				neededRow = i;
				break;
			}
		}
	}

	private int getGroupCount() {
		Element row = rows.get(neededRow);
		int count = 0;

		for (Element column : row.select("td")) {
			if (!column.text().isEmpty()) {
				count++;
			}
		}

		return count;
	}

	public boolean isGroupExists(int groupNum) throws Exception {
		Document mainDocument;

		try {
			mainDocument = Jsoup.connect("http://www.ntmm.ru/student/raspisanie.php").get();
		} catch (IOException e) {
			throw new Exception("Сайт недоступен!");
		}

		Elements elements = mainDocument.select("a[href]");

		for (Element hyperLink : elements) {
			String hyprText = hyperLink.text();
			if (hyprText.contains(String.valueOf(groupNum))) {
				return true;
			}
		}

		return false;
	}

	private void initUrl(int groupNum) throws Exception {
		Document mainDocument = Jsoup.connect("http://www.ntmm.ru/student/raspisanie.php").get();
		Elements elements = mainDocument.select("a[href]");

		for (Element hyperLink : elements) {
			String hyprText = hyperLink.text();

			if (hyprText.contains(String.valueOf(groupNum))) {
				url = "http://www.ntmm.ru" + hyperLink.attr("href").replace(".htm", ".files") + "/sheet001.htm";
				initDocument();

				return;
			}
		}

		throw new Exception("Такой группы не существует!");
	}

	private void initDocument() throws Exception {
		Document document;

		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new Exception("Сайт недоступен");
		}

		rows = Objects.requireNonNull(document.select("table").first()).select("tr");
	}

	private void initGroupColumn(int groupNum) {
		Element row = rows.get(neededRow);
		Elements columns = row.select("td");
		int out = -1;

		for (Element column : columns) {
			String group = column.text();

			if (!group.isEmpty()) {
				out++;
			}

			if (group.contains(String.valueOf(groupNum))) {
				groupColumn = out;
				return;
			}
		}
	}
}