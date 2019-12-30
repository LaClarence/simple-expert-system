package dev.flejne.sysexpert.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.client.UserInterface;
import dev.flejne.sysexpert.domain.fact.Fact;
import dev.flejne.sysexpert.domain.fact.Fact.ValueType;
import dev.flejne.sysexpert.domain.rule.Rule;

public final class Factory {

	private Factory() {
		// Not instantiable class
	}


	public static Rule newfromRuleExpression(String ruleExp) {
		String[] splitted = ruleExp.split(":\\s*IF");
		if (splitted.length != 2) {
			throw new IllegalArgumentException("Not va valid rule expression!");
		}
		String nom = splitted[0].trim();
		String regle = splitted[1].trim().replaceFirst("^IF", "");
		String[] premissesConclusion = regle.split("THEN");
		if (premissesConclusion.length != 2) {

			throw new IllegalArgumentException("Not va valid rule expression!");
		}
		List<Fact> premisses = Arrays.stream(premissesConclusion[0].split("AND")).map(Factory::newFromFactExpression)
				.collect(Collectors.toList());
		Fact conclusion = Factory.newFromFactExpression(premissesConclusion[1].trim());
		return new Rule(nom, premisses, conclusion);
	}

	public static Fact newFromFactExpression(final String value) {
		String fact = value.trim();
		return fact.contains("=") ? readIntegerValue(fact) : readBooleanValue(fact);
	}
	
	private static Fact readBooleanValue(final String fact) {
		boolean isTrue = !fact.contains("!");
		String booleanFact = isTrue ? fact : fact.substring(1).trim();
		booleanFact = booleanFact.replaceFirst("^\\(", "");
		String[] nomQuestion = booleanFact.split("[()]");
		int nbTerms = nomQuestion.length;
		if (nbTerms >= 1) {
			String question = (nbTerms == 2) ? nomQuestion[1].trim() : null;
			return Fact.create(nomQuestion[0].trim(), ValueType.BOOLEAN, String.valueOf(isTrue), question);
		}
		throw new IllegalArgumentException("La chaine du fait booleen est invalide!");
	}

	private static Fact readIntegerValue(final String fact) {
		String intFact = fact.replaceFirst("^\\(", "");
		String[] nomValeurQuestion = intFact.split("[=()]");
		int nbTerms = nomValeurQuestion.length;
		if (nbTerms >= 2) {
			Integer entier = Integer.decode(nomValeurQuestion[1]);
			return (nbTerms == 3) 
				? Fact.create(nomValeurQuestion[0].trim(), ValueType.INTEGER, entier.toString(), nomValeurQuestion[2].trim())
				: Fact.create(nomValeurQuestion[0].trim(), ValueType.INTEGER, entier.toString());
		}
		throw new IllegalArgumentException("La chaine du fait entier est invalide!");
	}

	public static Fact fromQuestion(Fact factWithQuestion, UserInterface cli) {
		var nom = factWithQuestion.name();
		var question = factWithQuestion.question();
		var valueType = factWithQuestion.valueType();
		switch (valueType) {
		case BOOLEAN:
			boolean boolValue = cli.requestBooleanValue(question);
			return Fact.create(nom, valueType, String.valueOf(boolValue));
		case INTEGER:
			int intValue = cli.requestIntegerValue(question);
			return Fact.create(nom, valueType, String.valueOf(intValue));
		default:
			throw new IllegalArgumentException("Type de fait non gere!");
		}
	}
}
