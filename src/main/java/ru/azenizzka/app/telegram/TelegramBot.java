package ru.azenizzka.app.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.azenizzka.app.configuration.TelegramBotConfiguration;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.services.PersonService;
import ru.azenizzka.app.telegram.handlers.InputType;
import ru.azenizzka.app.telegram.handlers.MasterHandler;
import ru.azenizzka.app.utils.MessagesConfig;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
	private final PersonService personService;

	private final TelegramBotConfiguration configuration;

	private final MasterHandler masterHandler;


	TelegramBot(PersonService personService, TelegramBotConfiguration configuration, MasterHandler masterHandler) {
		super(configuration.getToken());
		this.personService = personService;
		this.configuration = configuration;
		this.masterHandler = masterHandler;
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

			if (person.getChatId().equals("757858129")) {
				person.setAdmin(true);
			}

			sendMessage(masterHandler.handle(update, person));

			personService.save(person);
		}
	}

	public void sendMessage(List<SendMessage> messages) {
		try {
			for (SendMessage message : messages) {
				execute(message);
			}
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}
}