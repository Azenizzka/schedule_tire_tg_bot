package ru.azenizzka.app.telegram.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.azenizzka.app.utils.Consts;

public class ErrorMessage extends SendMessage {
	public ErrorMessage(String chatId, String errorMessage) {
		super(chatId, String.format(Consts.ERROR_TEMPLATE, errorMessage));
		enableMarkdown(true);
	}
}