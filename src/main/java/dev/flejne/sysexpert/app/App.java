package dev.flejne.sysexpert.app;

import java.util.List;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.Output;
import dev.flejne.sysexpert.client.UserInterface;
import dev.flejne.sysexpert.domain.fact.Fact;

public final class App {

	private App() {
		// Not instantiable class
	}

	public static void main(String[] args) {
		var userInterface = new UserInterface();
		InferencesEngine engine = new InferencesEngine(userInterface);
		var rules = RulesResource.loadRules();
		var facts = engine.resolve(rules);
		Output.log.info(() -> "Facts: " + toString(facts));
	}

	private static String toString(List<Fact> facts) {
		String solutions = "Found solution(s) are : \n";
		return solutions + facts.stream()
			.filter(Fact::positiveLevel)
			.sorted(Fact.byDecreasingLevel())
			.map(Fact::toString)
			.collect(Collectors.joining("\n"));
	}

}
