package ru.azenizzka.app.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;
import ru.azenizzka.app.telegram.commands.Command;
import ru.azenizzka.app.telegram.commands.BellCommand;
import ru.azenizzka.app.telegram.commands.InfoCommand;
import ru.azenizzka.app.telegram.commands.ReturnCommand;
import ru.azenizzka.app.telegram.messages.ErrorMessage;
import ru.azenizzka.app.utils.MessagesConfig;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandsHandler implements Handler {
	private final List<Command> commands;

	public CommandsHandler(BellCommand bellCommand, ReturnCommand returnCommand, InfoCommand infoCommand) {
		this.commands = new ArrayList<>();

		commands.add(returnCommand);
		commands.add(bellCommand);
		commands.add(infoCommand);
	}

	@Override
	public List<SendMessage> handle(Update update, Person person) {
		String textMessage = update.getMessage().getText().toLowerCase();

		for (Command command : commands) {
			if (command.isRequiredAdminRights() && !person.isAdmin()) {
				continue;
			}

			if (command.getCommand().equals(textMessage)) {
				return command.handle(update, person);
			}
		}

		SendMessage errorMessage = new ErrorMessage(update.getMessage().getChatId().toString(), MessagesConfig.UNKNOWN_COMMAND_EXCEPTION);


		return List.of(errorMessage);
	}
}
