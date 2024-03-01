package ru.azenizzka.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.azenizzka.app.telegram.keyboards.InputType;

@NoArgsConstructor
@Getter
@Setter

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String chatId;
	private int group;

	private String username;
	private boolean isAdmin;
	private InputType inputType;
}
