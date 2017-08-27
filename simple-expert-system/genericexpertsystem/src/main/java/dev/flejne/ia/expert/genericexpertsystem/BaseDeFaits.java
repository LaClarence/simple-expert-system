package dev.flejne.ia.expert.genericexpertsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class BaseDeFaits {
  private final List<FaitGenerique<?>> faits;

  public BaseDeFaits() {
    this.faits = new ArrayList<>();
  }

  public boolean add(FaitGenerique<?> fait) {
    Objects.requireNonNull(fait);
    return this.faits.add(fait);
  }

  public Optional<FaitGenerique<?>> chercher(String nom) {
    return this.faits.stream()
                     .filter(f -> f.getNom().equals(nom))
                     .findFirst();
  }

  public List<FaitGenerique<?>> getFaits() {
    return this.faits;
  }

}
