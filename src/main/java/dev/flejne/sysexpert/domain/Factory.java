package dev.flejne.sysexpert.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.flejne.sysexpert.domain.Fact.ValueType;

public final class Factory {


	private Factory() {
		// Not instantiable class
	}

	private static IllegalArgumentException invalidRuleExpression(String ruleExpression) {
		return new IllegalArgumentException("Rule expression is NOT valid: '"+ruleExpression+"'");
	}

	public static Rule buildRule(String ruleExpression) {
		String[] splitted = ruleExpression.split(":\\s*IF");
		if (splitted.length != 2) {
			throw invalidRuleExpression(ruleExpression);
		}
		String nom = splitted[0].trim();
		String regle = splitted[1].trim().replaceFirst("^IF", "");
		String[] premissesConclusion = regle.split("THEN");
		if (premissesConclusion.length != 2) {
			throw invalidRuleExpression(ruleExpression);
		}
		List<Fact> premisses = Arrays.stream(premissesConclusion[0].split("AND"))
				.map(Factory::buildFact)
				.collect(Collectors.toList());
		Fact conclusion = Factory.buildFact(premissesConclusion[1].trim());
		return new Rule(nom, premisses, conclusion);
	}

	public static Fact buildFact(final String factExpression) {
		String fact = factExpression.trim();
		return fact.contains("=") ? readIntegerValue(fact) : readBooleanValue(fact);
	}
	
	private static String removeFirstParenthesis(String fact) {
		return fact.replaceFirst("^\\(", "");
	}

	private static String[] split(String fact) {
		return fact.split("[=()]");
	}

	private static IllegalArgumentException invalidFactType(ValueType type) {
		return new IllegalArgumentException(String.format("%s fact expression is not valid!", type.name()));
	}

	
	private static Fact readBooleanValue(final String fact) {
		boolean isTrueCondition = !fact.contains("!");
		String booleanFact = isTrueCondition ? fact : fact.substring(1).trim();
		booleanFact = removeFirstParenthesis(booleanFact);
		String[] nomQuestion = split(booleanFact);
		int nbTerms = nomQuestion.length;
		if (nbTerms >= 1) {
			String question = (nbTerms == 2) ? nomQuestion[1].trim() : null;
			return Fact.create(nomQuestion[0].trim(), ValueType.BOOLEAN, String.valueOf(isTrueCondition), question);
		}
		throw invalidFactType(ValueType.BOOLEAN);
	}

	private static Fact readIntegerValue(final String fact) {
		String intFact = removeFirstParenthesis(fact);
		String[] nomValeurQuestion = split(intFact);
		int nbTerms = nomValeurQuestion.length;
		if (nbTerms >= 2) {
			Integer entier = Integer.decode(nomValeurQuestion[1]);
			return (nbTerms == 3) 
				? Fact.create(nomValeurQuestion[0].trim(), ValueType.INTEGER, entier.toString(), nomValeurQuestion[2].trim())
				: Fact.create(nomValeurQuestion[0].trim(), ValueType.INTEGER, entier.toString());
		}
		throw invalidFactType(ValueType.INTEGER);
	}
}
