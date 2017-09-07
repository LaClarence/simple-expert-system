package dev.flejne.ia.sysexpert.engine.forward;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BaseDeRegles {
  private final List<Regle> regles;

  BaseDeRegles() {
    this.regles = new ArrayList<>();
  }

  BaseDeRegles(List<Regle> regles) {
    this.regles = regles.stream()
                        .map(Regle::new)
                        .collect(Collectors.toList());
  }

  public boolean add(Regle r) {
    return this.regles.add(r);
  }

  public void supprimeRegles() {
    this.regles.clear();
  }

  /**
   * Ici la liste pourrait modifier de l'exterieur
   * 
   * @return
   */
  public List<Regle> getRegles() {
    return this.regles;
  }

  public boolean remove(Regle r) {
    return this.regles.remove(r);
  }
}
