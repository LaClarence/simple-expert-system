package dev.flejne.sysexpert.fact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Facts {
  private final List<Fact<?>> facts;

  public Facts() {
    this.facts = new ArrayList<>();
  }

  public boolean add(Fact<?> fait) {
    Objects.requireNonNull(fait);
    return this.facts.add(fait);
  }

  public Optional<Fact<?>> chercher(String name) {
    return this.facts.stream()
      .filter(f -> f.name().equals(name))
      .findFirst();
  }

  public List<Fact<?>> getFaits() {
    return this.facts;
  }
  
	private Comparator<? super Fact<?>> parNiveauDecroissant() {
		return (f1, f2) -> f1.level() - f2.level();
	}

	public String toString() {
		String afficheFaits = "Solution(s) trouvée(s) : \n";
		return afficheFaits + facts.stream().filter(f -> f.level() != 0).sorted(parNiveauDecroissant())
				.map(Fact::toString).collect(Collectors.joining("\n"));

	}


}
