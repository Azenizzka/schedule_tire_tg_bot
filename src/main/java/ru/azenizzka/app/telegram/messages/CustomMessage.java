package ru.azenizzka.app.telegram.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.azenizzka.app.telegram.keyboards.*;

public class CustomMessage extends SendMessage {
	public CustomMessage(String chatId, KeyboardType keyboardType) {
		setChatId(chatId);
		enableMarkdown(true);

		switch (keyboardType) {
			case MAIN -> MainKeyboard.addKeyboard(this);
			case SETTINGS_MAIN -> SettingsMainKeyboard.addKeyboard(this);
			case BELL_TYPE -> BellTypeKeyboard.addKeyboard(this);
			case RETURN_TYPE -> ReturnKeyboard.addKeyboard(this);
		}
	}
}
