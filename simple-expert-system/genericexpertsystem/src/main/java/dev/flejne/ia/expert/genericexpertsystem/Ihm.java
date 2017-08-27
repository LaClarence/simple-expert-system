package dev.flejne.ia.expert.genericexpertsystem;

import java.util.List;

public interface Ihm {
  void afficherFaits(List<FaitGenerique<?>> faits);

  void afficherRegles(List<Regle> regles);

  boolean demanderValeurBoolean(String question);

  int demanderValeurEntiere(String question);
}
