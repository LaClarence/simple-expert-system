package dev.flejne.sysexpert.domain.fact;

import java.util.Comparator;

public final class Fact {

	public static Fact create(String nom, ValueType valueType, String valeur, String question) {
		return new Fact(nom, valueType, valeur, question, 0);
	}

	public static Fact create(String nom, ValueType valueType, String valeur) {
		return create(nom, valueType, valeur, null);
	}

	public static Comparator<Fact> byDecreasingLevel() {
		return (f1, f2) -> f2.level() - f1.level();
	}

	public static boolean positiveLevel(Fact fact) {
		return fact.level() > 0;
	}

	public enum ValueType { BOOLEAN, INTEGER }

	private final int level;
	private final String name;
	private final String question;
	private final boolean hasQuestion;
	private final ValueType valueType;
	private final String value;

	private Fact(String nom, ValueType valueType, String valeur, String question, Integer niveau) {
		this.name = nom;
		this.valueType=valueType;
		this.value = valeur;
		this.question = question != null ? question.trim() : "";
		this.hasQuestion = !this.question.isEmpty();
		this.level = niveau;
	}

	public int level() {
		return this.level;
	}

	public String name() {
		return this.name;
	}

	public String question() {
		return this.question;
	}

	public boolean hasQuestion() {
		return this.hasQuestion;
	}

	public ValueType valueType() {
		return this.valueType;
	}

	public String value() {
		return this.value;
	}

	@Override
	public String toString() {
		String fact = name() + "(" + level() + ")=" + value();
		if (hasQuestion())
			fact += " (" + question() + ")";
		return fact;
	}

	public Fact updateLevel(int n) {
		return new Fact(this.name(), this.valueType(), this.value(), this.question(), n);
	}
}
