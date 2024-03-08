package ru.azenizzka.app.telegram.messages;

import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.azenizzka.app.telegram.keyboards.*;


@NoArgsConstructor
public class CustomMessage extends SendMessage {
	public CustomMessage(String chatId, KeyboardType keyboardType) {
		setChatId(chatId);
		enableMarkdown(true);

		switch (keyboardType) {
			case MAIN -> MainKeyboard.addKeyboard(this);
			case SETTINGS_MAIN -> SettingsMainKeyboard.addKeyboard(this);
			case BELL -> BellTypeKeyboard.addKeyboard(this);
			case RETURN -> ReturnKeyboard.addKeyboard(this);
			case DAY -> DayKeyboard.addKeyboard(this);
		}
	}

	public CustomMessage(String chatId, KeyboardType keyboardType, String text) {
		this(chatId, keyboardType);
		setText(text);
	}
}
