package dev.flejne.sysexpert.rule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class RulesResource {
	
	public static Supplier<Stream<String>> shapes() {
		return () -> fromFile("shapes.rules");
	}

	private static Stream<String> fromFile(String pathname) {
		try {
			return Files
				.lines(new File(pathname).toPath(), StandardCharsets.UTF_8)
				.map(s -> s.trim())
				.filter(s -> !s.isEmpty())
				.filter(s -> s.startsWith("R"));
		} catch (IOException e) {
			System.err.println("failed to load file '"+pathname+"'");
			e.printStackTrace();
		}
		return Stream.empty();
	}

	private RulesResource() {
		// Utility class
	}
}
