package dev.flejne.ia.expert.genericexpertsystem;

import java.util.List;
import java.util.stream.Collectors;

public class Regle {
  private final Fait<?> conclusion;
  private final String nom;
  private final List<Fait<?>> premisses;

  public Regle(Regle regle) {
    this.nom = regle.getNom();
    this.premisses = regle.getPremisses();
    this.conclusion = regle.getConclusion();
  }

  public Regle(String nom, List<Fait<?>> premisses, Fait<?> conclusion) {
    this.nom = nom;
    this.premisses = premisses;
    this.conclusion = conclusion;
  }

  public Fait<?> getConclusion() {
    return this.conclusion;
  }

  public String getNom() {
    return this.nom;
  }

  public List<Fait<?>> getPremisses() {
    return this.premisses;
  }

  @Override
  public String toString() {
    String regle = this.nom + " : IF (";
    regle += this.premisses.stream()
                           .map(Fait::toString)
                           .collect(Collectors.joining(" AND "));
    return regle + ") THEN " + this.conclusion.toString();
  }
}
