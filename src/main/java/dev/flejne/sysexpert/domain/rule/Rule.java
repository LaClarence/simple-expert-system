package dev.flejne.sysexpert.domain.rule;

import java.util.List;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.domain.fact.Fact;

public class Rule {
	private final Fact conclusion;
	private final String name;
	private final List<Fact> premises;

	public Rule(Rule rule) {
		this.name = rule.name();
		this.premises = rule.premises();
		this.conclusion = rule.conclusion();
	}

	public Rule(String name, List<Fact> premises, Fact conclusion) {
		this.name = name;
		this.premises = premises;
		this.conclusion = conclusion;
	}

	public Fact conclusion() {
		return this.conclusion;
	}

	public String name() {
		return this.name;
	}

	public List<Fact> premises() {
		return this.premises;
	}

	@Override
	public String toString() {
		String regle = this.name + " : IF (";
		regle += this.premises
				.stream()
				.map(Fact::toString)
				.collect(Collectors.joining(" AND "));
		return regle + ") THEN " + this.conclusion.toString();
	}

}
