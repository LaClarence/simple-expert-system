package dev.flejne.ia.expert.genericexpertsystem;

import java.util.List;
import java.util.stream.Collectors;

public class Regle {
  private final FaitGenerique<?> conclusion;
  private final String nom;
  private final List<FaitGenerique<?>> premisses;

  public Regle(Regle regle) {
    this.nom = regle.getNom();
    this.premisses = regle.getPremisses();
    this.conclusion = regle.getConclusion();
  }

  public Regle(String nom, List<FaitGenerique<?>> premisses, FaitGenerique<?> conclusion) {
    this.nom = nom;
    this.premisses = premisses;
    this.conclusion = conclusion;
  }

  public FaitGenerique<?> getConclusion() {
    return this.conclusion;
  }

  public String getNom() {
    return this.nom;
  }

  public List<FaitGenerique<?>> getPremisses() {
    return this.premisses;
  }

  @Override
  public String toString() {
    String regle = this.nom + " : IF (";
    regle += this.premisses.stream()
                           .map(FaitGenerique::toString)
                           .collect(Collectors.joining(" AND "));
    return regle + ") THEN " + this.conclusion.toString();
  }
}
