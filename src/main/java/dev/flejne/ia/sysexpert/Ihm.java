package dev.flejne.ia.sysexpert;

import java.util.List;

public interface Ihm {
  void afficherFaits(List<Fait<?>> faits);

  void afficherRegles(List<Regle> regles);

  boolean demanderValeurBoolean(String question);

  int demanderValeurEntiere(String question);
}
