package dev.flejne.ia.sysexpert.engine.forward;

public final class FaitEntier extends FaitUnit<Integer> {

    private FaitEntier(
        String nom,
        Integer valeur,
        String question,
        Integer niveau)
    {
        super(nom, valeur, question, niveau);
    }

    public FaitEntier(String nom, Integer valeur, String question)
    {
        this(nom, valeur, question, 0);
    }

    /**
     * Lorsque le fait est inferé il n'y a pas de question.
     * 
     * @param nom
     * @param valeur
     */
    public FaitEntier(String nom, Integer valeur)
    {
        this(nom, valeur, null, 0);
    }

    @Override
    public String toString()
    {
        return getNom() + "=" + getValeur() + " (" + getNiveau() + ")";
    }

    @Override
    public Fait<Integer> avecNiveau(int n)
    {
        return new FaitEntier(this.getNom(), this.getValeur(),
                this.getQuestion(), n);
    }
}
