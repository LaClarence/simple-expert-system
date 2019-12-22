package dev.flejne.sysexpert.app;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

public final class UserInterface {
	
	private static final String TRUE_REGEXP = "[YyOo]|[Oo]ui|[Yy]es|[Tt]rue";

	private final Scanner scanner = new Scanner(System.in);

	
	private <T> T demanderValeur(String question, Supplier<T> supplier, String errorMessage) {
		System.out.print("Question: " + question + " ");
		while (true) {
			try {
				return supplier.get();
			} catch (NoSuchElementException | IllegalStateException e) {
				System.err.println(errorMessage + " : " + e.getLocalizedMessage());
			}
		}
	}

	public boolean demanderValeurBoolean(String question) {
		return demanderValeur(question, () -> this.scanner.next().matches(TRUE_REGEXP), " A boolean is expected!");
	}

	public int demanderValeurEntiere(String question) {
		return demanderValeur(question, () -> this.scanner.nextInt(), "An integer is expected!");
	}


}
