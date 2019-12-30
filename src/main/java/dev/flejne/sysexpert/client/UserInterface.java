package dev.flejne.sysexpert.client;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

public final class UserInterface {

	private static final String TRUE_REGEXP = "[YyOo]|[Oo]ui|[Yy]es|[Tt]rue";
	private static final int MAX_USER_TRY = 6;
	private final Scanner scanner = new Scanner(System.in);

	private <T> T askUserValue(String question, Supplier<T> supplier, String errorMessage) {
		System.out.print("Question: " + question + " ");
		int invalidInput = MAX_USER_TRY;
		while (invalidInput > 0) {
			try {
				return supplier.get();
			} catch (NoSuchElementException | IllegalStateException e) {
				System.err.println(errorMessage + " : " + e.getLocalizedMessage());
				invalidInput--;
			}
		}
		throw new IllegalStateException("User input reach maximun error alllowed!");
	}

	public boolean requestBooleanValue(String question) {
		return askUserValue(question, () -> this.scanner.next().matches(TRUE_REGEXP), " A boolean value is expected, !");
	}

	public int requestIntegerValue(String question) {
		return askUserValue(question, this.scanner::nextInt, "An integer value is expected!");
	}


}
