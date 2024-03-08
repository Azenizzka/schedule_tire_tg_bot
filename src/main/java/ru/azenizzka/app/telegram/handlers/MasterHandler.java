package ru.azenizzka.app.telegram.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.configuration.TelegramBotConfiguration;
import ru.azenizzka.app.entities.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class MasterHandler implements Handler {

	private final CommandsHandler commandsHandler;
	private final BellTypeHandler bellTypeHandler;
	private final ChangeGroupHandler changeGroupHandler;
	private final SettingHandler settingHandler;
	private final RecessHandler recessHandler;
	private final AuditLogHandler auditLogHandler;

	private final TelegramBotConfiguration configuration;

	public MasterHandler(TelegramBotConfiguration configuration, CommandsHandler commandsHandler, BellTypeHandler bellTypeHandler, ChangeGroupHandler changeGroupHandler, SettingHandler settingHandler, RecessHandler recessHandler, AuditLogHandler auditLogHandler, TelegramBotConfiguration configuration1) {
		this.commandsHandler = commandsHandler;
		this.bellTypeHandler = bellTypeHandler;
		this.changeGroupHandler = changeGroupHandler;
		this.settingHandler = settingHandler;
		this.recessHandler = recessHandler;
		this.auditLogHandler = auditLogHandler;
		this.configuration = configuration1;
	}

	@Override
	public List<SendMessage> handle(Update update, Person person) {
		List<SendMessage> messages = new ArrayList<>();

		messages.addAll(auditLogHandler.handle(update, person));

		switch (person.getInputType()) {
			case COMMAND -> messages.addAll(commandsHandler.handle(update, person));
			case BELL_TYPE -> messages.addAll(bellTypeHandler.handle(update, person));
			case GROUP -> messages.addAll(changeGroupHandler.handle(update, person));
			case SETTINGS_MAIN -> messages.addAll(settingHandler.handle(update, person));
			case DAY -> messages.addAll(recessHandler.handle(update, person));
		}

		return messages;
	}
}
