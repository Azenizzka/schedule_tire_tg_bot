package ru.azenizzka.app.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.services.PersonService;
import ru.azenizzka.app.telegram.messages.CustomMessage;
import ru.azenizzka.app.utils.MessagesConfig;

import javax.crypto.CipherInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class BroadcastCommand implements Command {
	private final PersonService personService;

	public BroadcastCommand(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public String getCommand() {
		return MessagesConfig.BROADCAST_COMMAND;
	}

	@Override
	public boolean isRequiredAdminRights() {
		return true;
	}

	@Override
	public List<SendMessage> handle(Update update, Person person) {
		List<SendMessage> list = new ArrayList<>();
		SendMessage message;

		String broadcastText = update.getMessage().getText().replace(getCommand(), "");

		System.out.println(broadcastText);

		for (Person client : personService.findAll()) {
			message = new CustomMessage();
			message.setChatId(client.getChatId());
			message.setText(String.format(MessagesConfig.MESSAGE_FROM_ADMIN_TEMPLATE, broadcastText));

			list.add(message);
		}

		return list;
	}
}
