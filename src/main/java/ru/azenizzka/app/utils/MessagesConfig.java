package ru.azenizzka.app.utils;

public class MessagesConfig {
	public static final String RETURN_COMMAND = "/назад";
	public static final String RECESS_SCHEDULE_COMMAND = "/звонки";
	public static final String LESSON_SCHEDULE_COMMAND = "/расписание";
	public static final String SETTINGS_COMMAND = "/настройки";
	public static final String CHANGE_GROUP_COMMAND = "/сменить_группу";


	public static final String INFO_COMMAND = "/info";


	public static final String INFO_HEADER = "Список пользователей\n\n";


	public static final String HELP_MESSAGE = "help message";
	public static final String BELL_MESSAGE = "✏\uFE0F *Введите тип звонков*";
	public static final String CHANGE_GROUP_MESSAGE = "✏\uFE0F Введите номер вашей группы.\n\nПример: Если ваша группа *МР-232*, то вводите *232*.";
	public static final String SUCCESS_CHANGE_GROUP_MESSAGE = "\uD83D\uDFE2 Вы усппешно сменили группу";





	public static final String ERROR_TEMPLATE = "⭕\uFE0F Что-то пошло не так..\n" +
			"Текст ошибки: *%s*.\n\n" +
			"\uD83D\uDC68\uD83C\uDFFF\u200D\uD83D\uDCBB Обратная связь: @Azenizzka";





	public static final String BELL_TYPE_CONVERT_EXCEPTION = "Некорректный типа звонков не существует";
	public static final String UNKNOWN_COMMAND_EXCEPTION = "Такой команды не существует";
	public static final String GROUP_NOT_FOUND_EXCEPTION = "Такой группы не существует";

}