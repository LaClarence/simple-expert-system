package dev.flejne.sysexpert.app;

import java.util.ArrayList;
import java.util.List;

import dev.flejne.sysexpert.client.UserValueSupplier;
import dev.flejne.sysexpert.domain.Fact;
import dev.flejne.sysexpert.domain.Rule;

public final class InferencesEngine {

	private final UserValueSupplier userSupplier;

	InferencesEngine(UserValueSupplier userSupplier) {
		this.userSupplier = userSupplier;
	}

	public List<Fact> resolve(List<Rule> rules) {
		var facts = new ArrayList<Fact>();
		boolean isRuleApplicable =false;
		do {
			for (var rule : rules) {
				isRuleApplicable = rule.isApplicable(facts, userSupplier);
				if (isRuleApplicable) {
					rules.remove(rule);
					break;
				}
			}
		} while (isRuleApplicable);
		return facts;
		
	}

}
