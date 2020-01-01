package dev.flejne.sysexpert;

import java.util.logging.Logger;

public final class Output {

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%n %5$s ");
	}

	public static final Logger log = Logger.getLogger("log.sysexpert");

	private Output() {
		// Not instantiable class
	}
}
