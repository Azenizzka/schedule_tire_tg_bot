package ru.azenizzka.app.telegram.keyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.azenizzka.app.utils.MessagesConfig;

import java.util.ArrayList;
import java.util.List;

public class ReturnKeyboard {
	public static void addKeyboard(SendMessage message) {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();

		row.add(MessagesConfig.SETTINGS_COMMAND);
		keyboard.add(row);

		keyboardMarkup.setResizeKeyboard(true);
		keyboardMarkup.setKeyboard(keyboard);
		message.setReplyMarkup(keyboardMarkup);
	}
}