package ru.azenizzka.app.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.repositories.PersonRepository;
import ru.azenizzka.app.telegram.InputType;
import ru.azenizzka.app.telegram.keyboards.KeyboardType;
import ru.azenizzka.app.telegram.messages.CustomMessage;
import ru.azenizzka.app.utils.MessagesConfig;

@Component
public class ReturnCommand implements Command {
	PersonRepository personRepository;

	@Override
	public String getCommand() {
		return MessagesConfig.RETURN_COMMAND;
	}

	@Override
	public boolean isRequiredAdminRights() {
		return false;
	}

	@Override
	public SendMessage handle(Update update, Person person) {
		CustomMessage message = new CustomMessage(person.getChatId(), KeyboardType.MAIN);

		message.setText(MessagesConfig.RETURN_COMMAND);

		person.setInputType(InputType.COMMAND);

		return message;
	}
}
