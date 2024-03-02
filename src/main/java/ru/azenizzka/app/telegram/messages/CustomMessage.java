package ru.azenizzka.app.telegram.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CustomMessage extends SendMessage {
	public CustomMessage(String chatId) {
		setChatId(chatId);
		enableMarkdown(true);
	}
}
