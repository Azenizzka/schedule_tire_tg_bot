package ru.azenizzka.app.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.telegram.commands.Command;
import ru.azenizzka.app.telegram.commands.RecessScheduleCommand;
import ru.azenizzka.app.telegram.messages.ErrorMessage;
import ru.azenizzka.app.utils.Consts;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandsHandler {
	private final List<Command> commands;

	public CommandsHandler(RecessScheduleCommand recessScheduleCommand) {
		this.commands = new ArrayList<>();

		commands.add(recessScheduleCommand);
	}

	public SendMessage handleCommand(Update update) {
		String messageLowerText = update.getMessage().getText().toLowerCase();

		for (Command command : commands) {
			if (command.getCommand().equals(messageLowerText)) {
				return command.handle(update);
			}
		}

		return new ErrorMessage(update.getMessage().getChatId().toString(), Consts.UNKNOWN_COMMAND);
	}
}
