package dev.flejne.ia.expert.genericexpertsystem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MoteurInferences {

  private final static int NOT_APPLICABLE_RULE = -1;
  private final BaseDeFaits baseDeFaits;
  private final BaseDeRegles baseDeRegles;
  private final Ihm ihm;

  private Integer niveauMaxDeRegles;

  public MoteurInferences(Ihm ihm) {
    this.ihm = ihm;
    this.baseDeFaits = new BaseDeFaits();
    this.baseDeRegles = new BaseDeRegles();
    this.niveauMaxDeRegles = 0;
  }

  private boolean ajouterRegle(String newRule) {
    String[] splitted = newRule.split(":");
    if (splitted.length != 2) {
      return false;
    }
    String nom = splitted[0].trim();
    String regle = splitted[1].trim()
                              .replaceFirst("^IF", "");
    String[] premissesConclusion = regle.split("THEN");
    if (premissesConclusion.length != 2) {
      return false;
    }
    List<FaitGenerique<?>> premisses = Arrays.stream(premissesConclusion[0].split("AND"))
                                                    .map(FaitFactory::fromString)
                                                    .collect(Collectors.toList());
    FaitGenerique<?> conclusion = FaitFactory.fromString(premissesConclusion[1].trim());
    return this.baseDeRegles.add(new Regle(nom, premisses, conclusion));
  }

  public boolean ajouterRegles(List<String> nouvellesRegles) {
    return nouvellesRegles.stream()
                          .map(this::ajouterRegle)
                          .reduce(true, (r1, r2) -> r1 && r2);
  }

  public boolean demanderValeurBoolean(String question) {
    return this.ihm.demanderValeurBoolean(question);
  }

  public int demanderValeurEntiere(String question) {
    return this.ihm.demanderValeurEntiere(question);
  }

  /**
   * Test si une regle provenant d'une base de regles est applicable
   *
   * @param regle
   *          de la base de regles
   * @return le niveau de la regle sinon -1 lorsque:<br>
   *         <li>la regle ne peut pas s'appliquer
   *         <li>la valeur du fait de la base de faits ou qui vient d'être creer ne correspond pas avec la valeur du fait de la
   *         regle
   */
  public int estApplicable(Regle regle) {
    int niveauMax = 0;
    for (FaitGenerique<?> premisse : regle.getPremisses()) {
      Optional<FaitGenerique<?>> fait = this.baseDeFaits.chercher(premisse.getNom());
      if (!fait.isPresent()) {
        if (premisse.hasQuestion()) {
           FaitGenerique<?> nouveauFait = FaitFactory.fromQuestion(premisse, this);
           this.baseDeFaits.add(nouveauFait);
           fait = Optional.of(nouveauFait);
        } else {
          return NOT_APPLICABLE_RULE;
        }
      }

      Optional<Integer> niveauFait = fait.filter(f -> f.getValeur()
                                                       .equals(premisse.getValeur()))
                                         .map(FaitGenerique::getNiveau);
      
      if (!niveauFait.isPresent()) {
        return NOT_APPLICABLE_RULE;
      }
      niveauMax = Math.max(niveauMax, niveauFait.get());
    }
    return niveauMax;
  }

  public void resoudre() {
    BaseDeRegles baseDeReglesLocale = new BaseDeRegles(this.baseDeRegles.getRegles());
    this.baseDeRegles.supprimeRegles();
    Optional<Regle> opRegle = trouverPremiereRegleApplicable(baseDeReglesLocale);
    while (opRegle.isPresent()) {
      Regle r = opRegle.get();
      int niveauConclusion = this.niveauMaxDeRegles + 1;
      FaitGenerique<?> conclusion = r.getConclusion().avecNiveau(niveauConclusion);
      this.baseDeFaits.add(conclusion);
      baseDeReglesLocale.remove(r);
      opRegle = trouverPremiereRegleApplicable(baseDeReglesLocale);
    }
    this.ihm.afficherFaits(this.baseDeFaits.getFaits());
  }
  

  /**
   * Retourne la premiere regle applicalbe parmi la liste de regles.<br>
   * Le niveau Max des regles devient celui de la regle applicable.
   * @param baseDeRegles liste des regles non traités
   * @return la prermiere regle applicable trouvé
   */
  public Optional<Regle> trouverPremiereRegleApplicable(BaseDeRegles baseDeRegles) {
    for (Regle r : baseDeRegles.getRegles()) {
      int niveau = estApplicable(r);
      if (niveau != NOT_APPLICABLE_RULE) {
        this.niveauMaxDeRegles = niveau;
        System.out.println("niveau Max regale = "+ this.niveauMaxDeRegles);
        return Optional.of(r);
      }
    }
    return Optional.empty();
  }
}
