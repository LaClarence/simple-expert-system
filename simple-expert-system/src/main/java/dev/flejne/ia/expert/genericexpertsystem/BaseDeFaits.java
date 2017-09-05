package dev.flejne.ia.expert.genericexpertsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class BaseDeFaits {
  private final List<Fait<?>> faits;

  public BaseDeFaits() {
    this.faits = new ArrayList<>();
  }

  public boolean add(Fait<?> fait) {
    Objects.requireNonNull(fait);
    return this.faits.add(fait);
  }

  public Optional<Fait<?>> chercher(String nom) {
    return this.faits.stream()
                     .filter(f -> f.getNom().equals(nom))
                     .findFirst();
  }

  public List<Fait<?>> getFaits() {
    return this.faits;
  }

}
