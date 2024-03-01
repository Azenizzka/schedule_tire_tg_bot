package ru.azenizzka.app.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.utils.Consts;

@Component
public class RecessScheduleCommand implements Command {
	@Override
	public String getCommand() {
		return Consts.RECESS_SCHEDULE_COMMAND;
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
