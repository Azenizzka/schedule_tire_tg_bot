package ru.azenizzka.app.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.azenizzka.app.entities.Person;

public interface Handler {
	SendMessage handle(Update update, Person person);
}
