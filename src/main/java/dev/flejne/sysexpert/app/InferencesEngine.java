package dev.flejne.sysexpert.app;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.flejne.sysexpert.fact.Fact;
import dev.flejne.sysexpert.fact.Factory;
import dev.flejne.sysexpert.fact.Facts;
import dev.flejne.sysexpert.rule.Rule;
import dev.flejne.sysexpert.rule.Rules;

public final class InferencesEngine {

	private final Facts facts;
	private final Rules rules;
	private final UserInterface cli;
	private int niveauMaxDeRegles;

	public InferencesEngine(final UserInterface userInterface) {
		this.cli = userInterface;
		this.facts = new Facts();
		this.rules = new Rules();
		this.niveauMaxDeRegles = 0;
	}

	private boolean ajouterRegle(String newRule) {
		String[] splitted = newRule.split(":");
		if (splitted.length != 2) {
			return false;
		}
		String nom = splitted[0].trim();
		String regle = splitted[1].trim().replaceFirst("^IF", "");
		String[] premissesConclusion = regle.split("THEN");
		if (premissesConclusion.length != 2) {
			return false;
		}
		List<Fact<?>> premisses = Arrays
				.stream(premissesConclusion[0].split("AND"))
				.map(Factory::fromString)
				.collect(Collectors.toList());
		Fact<?> conclusion = Factory.fromString(premissesConclusion[1].trim());
		return this.rules.add(new Rule(nom, premisses, conclusion));
	}

	public boolean addRules(Supplier<Stream<String>> nouvellesRegles) {
		return nouvellesRegles.get()
				.map(this::ajouterRegle)
				.reduce(true, (r1, r2) -> r1 && r2);
	}

	/**
	 * Test si une regle provenant d'une base de regles est applicable
	 *
	 * @param regle de la base de regles
	 * @return le niveau de la regle sinon -1 lorsque:<br>
	 *         <li>la regle ne peut pas s'appliquer
	 *         <li>la valeur du fait de la base de faits ou qui vient d'être creer
	 *         ne correspond pas avec la valeur du fait de la regle
	 */
	public boolean isApplicable(Rule regle) {
		for (Fact<?> premisse : regle.premisses()) {
			Optional<Fact<?>> fait = this.facts.chercher(premisse.name());
			if (!fait.isPresent()) {
				if (premisse.hasQuestion()) {
					Fact<?> nouveauFait = Factory.fromQuestion(premisse, this.cli);
					this.facts.add(nouveauFait);
					fait = Optional.of(nouveauFait);
				} else {
					return false;
				}
			}

			Optional<Integer> niveauFait = fait.filter(f -> f.value().equals(premisse.value()))
					.map(Fact::level);

			if (!niveauFait.isPresent()) {
				return false;
			}
			this.niveauMaxDeRegles = Math.max(0, niveauFait.get());
		}
		return true;
	}

	public void solve() {
		Rules baseDeReglesLocale = new Rules(this.rules);
		this.rules.clear();
		Optional<Rule> opRegle = baseDeReglesLocale.firstApplicableRule(this::isApplicable);
		while (opRegle.isPresent()) {
			Rule r = opRegle.get();
			ajouteConclusionDansBaseDeFaits(r);
			baseDeReglesLocale.remove(r);
			opRegle = baseDeReglesLocale.firstApplicableRule(this::isApplicable);
		}

		System.out.println(this.facts);
		System.out.println("** Résolution terminés. **");

	}

	private void ajouteConclusionDansBaseDeFaits(Rule r) {
		int niveauConclusion = this.niveauMaxDeRegles + 1;
		Fact<?> conclusion = r.conclusiveFact().handleLevel(niveauConclusion);
		this.facts.add(conclusion);

	}

}
