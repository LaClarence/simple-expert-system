package dev.flejne.sysexpert.client;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import dev.flejne.sysexpert.Output;
import dev.flejne.sysexpert.domain.Fact.ValueType;

public final class UserInterface{

	private static final Pattern TRUE_REGEXP = Pattern.compile("[YyOo]|[Oo]ui|[Yy]es|[Tt]rue");
	private static final int MAX_USER_TRY = 6;
	private final Scanner scanner = new Scanner(System.in,StandardCharsets.UTF_8);

	private <T> T askUserValue(String question, Supplier<T> supplier, String errorMessage) {
		int invalidInput = MAX_USER_TRY;
		while (invalidInput > 0) {
			Output.log.info(String.format("Question: %s ", question));
			try {
				return supplier.get();
			} catch (NoSuchElementException | IllegalStateException e) {
				Output.log.severe(() -> errorMessage + " : " + e);
				invalidInput--;
			}
			scanner.nextLine();
		}
		throw new IllegalStateException("User input reach maximun error alllowed!");
	}

	private boolean requestBooleanValue(String question) {
		return askUserValue(question, () -> { 
			var userInput = this.scanner.next();
			return TRUE_REGEXP.matcher(userInput).matches();
		}
		, " A boolean value is expected, !");
	}

	private int requestIntegerValue(String question) {
		return askUserValue(question, this.scanner::nextInt, "An integer value is expected!");
	}

	public String requestUserValue(ValueType valueType, String question) {
		switch (valueType) {
		case BOOLEAN:
			boolean boolValue = requestBooleanValue(question);
			return String.valueOf(boolValue);
		case INTEGER:
			int intValue = requestIntegerValue(question);
			return String.valueOf(intValue);
		default:
			throw new IllegalArgumentException("The type of the value for this fact is not supported!");
		}
	}
}
