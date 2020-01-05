package dev.flejne.sysexpert.domain;

import java.util.Comparator;
import dev.flejne.sysexpert.client.UserValueSupplier;

public final class Fact {

	public enum ValueType {BOOLEAN, INTEGER }

	public static Fact create(String name, ValueType valueType, String value, String question) {
		return new Fact(name, valueType, value, question, 0);
	}

	public static Fact create(Fact fact, String userValue) {
		return create(fact.name, fact.valueType, userValue, null);
	}

	public static Fact create(String name, ValueType valueType, String value) {
		return create(name, valueType, value, null);
	}

	public static Comparator<Fact> byDecreasingLevel() {
		return (f1, f2) -> f2.level() - f1.level();
	}

	public static boolean positiveLevel(Fact fact) {
		return fact.level() > 0;
	}

	private final int level;
	private final String name;
	private final String question;
	private final boolean hasQuestion;
	private final ValueType valueType;
	private final String value;

	private Fact(String name, ValueType valueType, String value, String question, Integer level) {
		this.name = name;
		this.valueType=valueType;
		this.value = value;
		this.question = question != null ? question.trim() : "";
		this.hasQuestion = !this.question.isEmpty();
		this.level = level;
	}

	public int level() {
		return this.level;
	}

	public boolean hasSameName(Fact fact) {
		return this.name.equals(fact.name);
	}

	public boolean hasSameValue(Fact fact) {
		return this.value.equals(fact.value);
	}

	public boolean hasQuestion() {
		return this.hasQuestion;
	}

	public Fact valueFromQuestion(UserValueSupplier supplier) {
		return create(this, supplier.valueOf(valueType, question));
	}

	@Override
	public String toString() {
		String fact = name + "(" + level() + ")=" + value;
		if (hasQuestion())
			fact += " (" + question + ")";
		return fact;
	}

	public Fact createWithLevel(int level) {
		return new Fact(this.name, this.valueType, this.value, this.question, level);
	}
}
