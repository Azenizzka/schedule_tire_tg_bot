package ru.azenizzka.app.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.repositories.PersonRepository;
import ru.azenizzka.app.utils.Consts;

@Component
public class ReturnCommand implements Command {
	PersonRepository personRepository;

	@Override
	public String getCommand() {
		return Consts.RETURN_COMMAND;
	}

	@Override
	public boolean isRequiredAdminRights() {
		return false;
	}

	@Override
	public SendMessage handle(Update update) {
		return null;
	}
}
