package dev.flejne.sysexpert.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Rules {
	private final List<Rule> rules;

	public Rules() {
		this.rules = new ArrayList<>();
	}

	public Rules(Rules rules) {
		this.rules = rules.all().stream().map(Rule::new).collect(Collectors.toList());
	}

	public boolean add(Rule r) {
		return this.rules.add(r);
	}

	public void clear() {
		this.rules.clear();
	}

	/**
	 * Ici la liste pourrait modifier de l'exterieur
	 * 
	 * @return
	 */
	private List<Rule> all() {
		return this.rules;
	}

	public Optional<Rule> firstApplicableRule(Predicate<Rule> isApplicable) {
		return this.rules.stream().filter(isApplicable).findFirst();
	}

	public boolean remove(Rule r) {
		return this.rules.remove(r);
	}

	public String toString() {
		return rules.stream().map(Rule::toString).collect(Collectors.joining("\n"));
	}

}
