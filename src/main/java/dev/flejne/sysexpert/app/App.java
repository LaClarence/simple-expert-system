package dev.flejne.sysexpert.app;

import java.util.List;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.Output;
import dev.flejne.sysexpert.client.UserInterface;
import dev.flejne.sysexpert.domain.Fact;

public final class App {

	private App() {
		// Not instantiable class
	}

	public static void main(String[] args) {
		var rules = RulesResource.loadRules();
		var cli = new UserInterface();
		var engine = new InferencesEngine(cli::requestUserValue);
		var facts = engine.resolve(rules);
		Output.log.info(() -> "Facts: " + toString(facts));
	}

	private static String toString(List<Fact> facts) {
		var solutions = "Found solution(s) are : \n";
		return solutions + facts.stream()
			.filter(Fact::positiveLevel)
			.sorted(Fact.byDecreasingLevel())
			.map(Fact::toString)
			.collect(Collectors.joining("\n"));
	}

}
