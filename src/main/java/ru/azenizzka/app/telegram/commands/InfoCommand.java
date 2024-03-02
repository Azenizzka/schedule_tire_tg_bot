package ru.azenizzka.app.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.services.PersonService;
import ru.azenizzka.app.telegram.keyboards.KeyboardType;
import ru.azenizzka.app.telegram.messages.CustomMessage;
import ru.azenizzka.app.utils.MessagesConfig;

import java.util.LinkedList;
import java.util.List;

@Component
public class InfoCommand implements Command {
	private final PersonService personService;

	public InfoCommand(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public String getCommand() {
		return MessagesConfig.INFO_COMMAND;
	}

	@Override
	public boolean isRequiredAdminRights() {
		return true;
	}

	@Override
	public List<SendMessage> handle(Update update, Person person) {
		SendMessage message = new CustomMessage(person.getChatId(), KeyboardType.MAIN);
		message.enableMarkdown(false);
		List<SendMessage> messages = new LinkedList<>();

		StringBuilder result = new StringBuilder();

		int counter = 0;
		boolean isSet = false;
		result.append(MessagesConfig.INFO_HEADER);
		for (Person user : personService.findAll()) {
			counter++;
			result.append("\uD83D\uDC68\uD83C\uDFFF\u200D\uD83E\uDDB2 @").append(user.getUsername()).append(" cID:").append(user.getChatId()).append("\n");
			result.append("\uD83D\uDCDA group: ").append(user.getGroupNum()).append("\n");
			result.append("\uD83D\uDC68\uD83C\uDFFF\u200D\uD83E\uDDB3 isAdmin: ");
			if (user.isAdmin()) {
				result.append("\uD83D\uDFE2\n\n");
			} else {
				result.append("\uD83D\uDD34\n\n");
			}

			if (counter == 10) {
				isSet = true;
				counter = 0;
				message.setText(result.toString());
				messages.add(message);
				message = new CustomMessage(person.getChatId(), KeyboardType.MAIN);
				message.enableMarkdown(false);
			}
		}

		if (!isSet) {
			message.setText(result.toString());
			messages.add(message);
		}

		return messages;
	}
}
