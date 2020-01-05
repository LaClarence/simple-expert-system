package dev.flejne.sysexpert.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.client.UserValueSupplier;

public final class Rule {
	private final Fact conclusion;
	private final String name;
	private final List<Fact> premises;

	public Rule(Rule rule) {
		this(rule.name, rule.premises, rule.conclusion);
	}

	public Rule(String name, List<Fact> premises, Fact conclusion) {
		this.name = name;
		this.premises = new ArrayList<>(premises);
		this.conclusion = conclusion;
	}

	/**
	 * Si la régle concernée est applicable, la conclusion de la régle est ajoutée à la base
	 * de fait. Sinon on retourne faux
	 *
	 * @param facts la base de faits courante
	 * @param rule la régle à considérer
	 * @return True lorsque la régle est applicable sinon False
	 */
	public boolean isApplicable(List<Fact> facts, UserValueSupplier userSupplier) {
		int maxLevel = -1;
		for (var premise : premises) {
			var factFound = facts.stream().filter(premise::hasSameName).findFirst();
			if (factFound.isEmpty()) {
				if (premise.hasQuestion()) {
					var newFact = premise.valueFromQuestion(userSupplier);
					facts.add(newFact);
					factFound = Optional.of(newFact);
				} else {
					return false;
				}
			}

			var factApplicable = factFound.get();
			if (! premise.hasSameValue(factApplicable)) {
				return false;
			}

			maxLevel = Math.max(maxLevel, factApplicable.level());
		}
		int levelOfConclusion = maxLevel + 1;
		var conclusionFact = conclusion.createWithLevel(levelOfConclusion);
		facts.add(conclusionFact);
		return true;
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
