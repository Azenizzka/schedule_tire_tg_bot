package ru.azenizzka.app.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class MasterHandler implements Handler {
	private final CommandsHandler commandsHandler;
	private final BellTypeHandler bellTypeHandler;
	private final ChangeGroupHandler changeGroupHandler;
	private final SettingHandler settingHandler;

	public MasterHandler(CommandsHandler commandsHandler, BellTypeHandler bellTypeHandler, ChangeGroupHandler changeGroupHandler, SettingHandler settingHandler) {
		this.commandsHandler = commandsHandler;
		this.bellTypeHandler = bellTypeHandler;
		this.changeGroupHandler = changeGroupHandler;
		this.settingHandler = settingHandler;
	}

	@Override
	public List<SendMessage> handle(Update update, Person person) {
		List<SendMessage> messages = new ArrayList<>();

		switch (person.getInputType()) {
			case COMMAND -> messages.addAll(commandsHandler.handle(update, person));
			case INPUT_BELL_TYPE -> messages.addAll(bellTypeHandler.handle(update, person));
			case INPUT_GROUP -> messages.addAll(changeGroupHandler.handle(update, person));
			case SETTINGS_MAIN -> messages.addAll(settingHandler.handle(update, person));
		}

		return messages;
	}
}