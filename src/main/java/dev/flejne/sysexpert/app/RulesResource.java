package dev.flejne.sysexpert.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.flejne.sysexpert.domain.Factory;
import dev.flejne.sysexpert.domain.rule.Rule;

public final class RulesResource {

	public static List<Rule> loadRules() {
		return shapes().get().map(Factory::newfromRuleExpression).collect(Collectors.toList());
	}

	private static Supplier<Stream<String>> shapes() {
		String rulesFile = System.getProperty("rules");
		if (rulesFile != null)
			return () -> fromFile(rulesFile);
		else
			return () -> loadShapesRules();
	}

	private static Stream<String> fromFile(String pathname) {
		try {
			return Files.lines(new File(pathname).toPath(), StandardCharsets.UTF_8).map(String::trim)
					.filter(s -> !s.isEmpty()).filter(s -> s.startsWith("R"));
		} catch (IOException e) {
			System.err.println("failed to load file '" + pathname + "'");
			e.printStackTrace();
		}
		return Stream.empty();
	}

	private static Stream<String> loadShapesRules() {
		try (InputStream input = RulesResource.class.getResourceAsStream("/shapes.rules");
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));) {
			 List<String> lines = reader.lines().collect(Collectors.toList());
			 return lines.stream();
		} catch (IOException e) {
			System.err.println("failed to load file 'shapes.rules'");
			e.printStackTrace();
		}
		return Stream.empty();
	}

	private RulesResource() {
		// Utility class
	}
}
