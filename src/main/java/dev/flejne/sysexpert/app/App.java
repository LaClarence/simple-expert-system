package dev.flejne.sysexpert.app;

import java.util.function.Supplier;
import java.util.stream.Stream;

import dev.flejne.sysexpert.rule.RulesResource;

public final class App {

	public static void main(String[] args) {
		App app = new App();
		app.run(RulesResource.shapes());
	}

	private App() {
		// 
	}

	private void run(final Supplier<Stream<String>> rules) {
		InferencesEngine engine = new InferencesEngine(new UserInterface());

		System.out.print("** Loading rules");
		if (! engine.addRules(rules))
			System.out.println(" failed! **");

		System.out.println(" succeeded. **");
		engine.solve();

	}

}
