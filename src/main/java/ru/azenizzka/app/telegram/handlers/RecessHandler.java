package ru.azenizzka.app.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.exceptions.BellTypeConvertException;
import ru.azenizzka.app.services.BellScheduleService;
import ru.azenizzka.app.services.LessonScheduleService;
import ru.azenizzka.app.telegram.keyboards.KeyboardType;
import ru.azenizzka.app.telegram.messages.CustomMessage;
import ru.azenizzka.app.telegram.messages.ErrorMessage;
import ru.azenizzka.app.utils.BellUtil;
import ru.azenizzka.app.utils.Day;
import ru.azenizzka.app.utils.DayUtil;
import ru.azenizzka.app.utils.MessagesConfig;

import java.util.List;

@Component
public class RecessHandler implements Handler {
	private final LessonScheduleService lessonScheduleService;

	public RecessHandler(LessonScheduleService lessonScheduleService) {
		this.lessonScheduleService = lessonScheduleService;
	}

	@Override
	public List<SendMessage> handle(Update update, Person person) {
		person.setInputType(InputType.COMMAND);

		CustomMessage message = new CustomMessage(person.getChatId(), KeyboardType.MAIN);
		String textMessage = update.getMessage().getText().toLowerCase();

		try {
			Day day = DayUtil.convertStrToDay(textMessage);

			System.out.println(day);

			List<List<String>> lessons = lessonScheduleService.getLessons(person.getGroupNum(), day);

			StringBuilder result = new StringBuilder("Расписание *" + person.getGroupNum() + "* группы\n*" + textMessage + "*\n\n");

			if (lessons.isEmpty()) {
				return List.of(new CustomMessage(person.getChatId(), KeyboardType.MAIN, MessagesConfig.NO_LESSONS_MESSAGE));
			}

			for (List<String> list : lessons) {
				result.append("*").append(list.get(0)).append(" пара:* ").append(list.get(1)).append("\n");
				result.append("*Кабинет:* ").append(list.get(2)).append("\n\n");
			}


			System.out.println(result);

			message.setText(result.toString());
		} catch (Exception e) {
			return List.of(new ErrorMessage(person.getChatId(), e.getMessage()));
		}

		return List.of(message);
	}
}
