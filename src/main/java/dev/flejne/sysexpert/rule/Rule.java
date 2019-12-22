package dev.flejne.sysexpert.rule;

import java.util.List;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.fact.Fact;

public class Rule {
	private final Fact<?> conclusion;
	private final String name;
	private final List<Fact<?>> premisses;

	public Rule(Rule rule) {
		this.name = rule.name();
		this.premisses = rule.premisses();
		this.conclusion = rule.conclusiveFact();
	}

	public Rule(String name, List<Fact<?>> premisses, Fact<?> conclusion) {
		this.name = name;
		this.premisses = premisses;
		this.conclusion = conclusion;
	}

	public Fact<?> conclusiveFact() {
		return this.conclusion;
	}

	public String name() {
		return this.name;
	}

	public List<Fact<?>> premisses() {
		return this.premisses;
	}

	@Override
	public String toString() {
		String regle = this.name + " : IF (";
		regle += this.premisses
				.stream()
				.map(Fact::toString)
				.collect(Collectors.joining(" AND "));
		return regle + ") THEN " + this.conclusion.toString();
	}
}
