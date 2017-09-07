package dev.flejne.ia.sysexpert.engine.forward;

import java.util.List;

public interface Ihm {
  void afficherFaits(List<Fait<?>> faits);

  void afficherRegles(List<Regle> regles);

  boolean demanderValeurBoolean(String question);

  int demanderValeurEntiere(String question);
}
