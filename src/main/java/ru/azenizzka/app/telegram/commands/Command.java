package ru.azenizzka.app.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
	String getCommand();

	boolean isRequiredAdminRights();

	SendMessage handle(Update update);
}
