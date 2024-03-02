package ru.azenizzka.app.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.azenizzka.app.configuration.TelegramBotConfiguration;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.services.PersonService;
import ru.azenizzka.app.telegram.commands.ReturnCommand;
import ru.azenizzka.app.telegram.handlers.CommandsHandler;
import ru.azenizzka.app.telegram.handlers.BellTypeHandler;
import ru.azenizzka.app.utils.MessagesConfig;

@Component
public class TelegramBot extends TelegramLongPollingBot {
	private final PersonService personService;

	private final TelegramBotConfiguration configuration;
	private final CommandsHandler commandsHandler;
	private final BellTypeHandler bellTypeHandler;


	TelegramBot(PersonService personService, TelegramBotConfiguration configuration, CommandsHandler commandsHandler, BellTypeHandler bellTypeHandler) {
		super(configuration.getToken());
		this.personService = personService;
		this.configuration = configuration;
		this.commandsHandler = commandsHandler;
		this.bellTypeHandler = bellTypeHandler;
	}

	@Override
	public String getBotUsername() {
		return configuration.getName();
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String chatId = update.getMessage().getChatId().toString();
			String username = update.getMessage().getChat().getUserName();
			Person person;

			if (!personService.isExistsByChatId(chatId)) {
				person = new Person();

				person.setChatId(chatId);
				person.setUsername(username);
				person.setInputType(InputType.COMMAND);

				personService.save(person);
			}

			person = personService.findByChatId(chatId);
			person.setUsername(username);

			// :TODO: FIX THIS GOVNO! ПЕРЕПИШИ НОРМАЛЬНО
			if (update.getMessage().getText().equals(MessagesConfig.RETURN_COMMAND)) {
				person.setInputType(InputType.COMMAND);
			}

			switch (person.getInputType()) {
				case COMMAND -> sendMessage(commandsHandler.handle(update, person));
				case INPUT_BELL_TYPE -> sendMessage(bellTypeHandler.handle(update, person));
			}

			personService.save(person);
		}
	}

	public void sendMessage(SendMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}
}