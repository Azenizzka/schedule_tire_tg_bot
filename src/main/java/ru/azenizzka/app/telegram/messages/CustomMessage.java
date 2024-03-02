package ru.azenizzka.app.telegram.messages;

import com.sun.tools.javac.Main;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.azenizzka.app.telegram.keyboards.BellTypeKeyboard;
import ru.azenizzka.app.telegram.keyboards.KeyboardType;
import ru.azenizzka.app.telegram.keyboards.MainKeyboard;

public class CustomMessage extends SendMessage {
	public CustomMessage(String chatId, KeyboardType keyboardType) {
		setChatId(chatId);
		enableMarkdown(true);

		switch (keyboardType) {
			case MAIN -> MainKeyboard.addKeyboard(this);
			case BELL_TYPE -> BellTypeKeyboard.addKeyboard(this);
		}
	}

	public CustomMessage(String chatId, KeyboardType keyboardType, String text) {
		setChatId(chatId);
		enableMarkdown(true);

		this.setText(text);

		switch (keyboardType) {
			case MAIN -> MainKeyboard.addKeyboard(this);
			case BELL_TYPE -> BellTypeKeyboard.addKeyboard(this);
		}
	}
}
