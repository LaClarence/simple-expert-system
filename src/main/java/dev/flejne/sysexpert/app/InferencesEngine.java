package dev.flejne.sysexpert.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.flejne.sysexpert.client.UserInterface;
import dev.flejne.sysexpert.domain.Factory;
import dev.flejne.sysexpert.domain.fact.Fact;
import dev.flejne.sysexpert.domain.rule.Rule;

public final class InferencesEngine {

	private final UserInterface cli;

	public InferencesEngine(final UserInterface userInterface) {
		this.cli = userInterface;
	}

	/**
	 * Si la régle concernée est applicable, la conclusion de la régle est ajoutée à la base
	 * de fait. Sinon on retourne faux
	 *
	 * @param facts la base de faits courante
	 * @param rule la régle à considérer
	 * @return True lorsque la régle est applicable sinon False
	 */
	public boolean isApplicable(List<Fact> facts, Rule rule) {
		int maxLevel = -1;
		for (var premise : rule.premises()) {
			var factFound = facts.stream().filter(f -> f.name().equals(premise.name())).findFirst();
			if (factFound.isEmpty()) {
				if (premise.hasQuestion()) {
					factFound = Optional.of(Factory.fromQuestion(premise, this.cli));
					facts.add(factFound.get());
				} else {
					return false;
				}
			}

			if (!factFound.get().value().equals(premise.value())) {
				return false;
			}

			maxLevel = Math.max(maxLevel, factFound.get().level());
		}
		int levelOfConclusion = maxLevel + 1;
		var conclusion = rule.conclusion().updateLevel(levelOfConclusion);
		facts.add(conclusion);
		
		return true;
	}

	public List<Fact> resolve(List<Rule> rules) {
		var facts = new ArrayList<Fact>();
		boolean isRuleApplicable =false;
		do {
			for (var rule : rules) {
				isRuleApplicable = isApplicable(facts, rule);
				if (isRuleApplicable) {
					rules.remove(rule);
					break;
				}
			}
		} while (isRuleApplicable);
		return facts;
		
	}

}
