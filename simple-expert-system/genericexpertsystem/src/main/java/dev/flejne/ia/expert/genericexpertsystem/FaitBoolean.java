package dev.flejne.ia.expert.genericexpertsystem;

public final class FaitBoolean extends Fait<Boolean> {

    private FaitBoolean(
        String nom,
        Boolean valeur,
        String question,
        Integer niveau)
    {
        super(nom, valeur, question, niveau);
    }

    public FaitBoolean(String nom, Boolean valeur, String question)
    {
        this(nom, valeur, question, 0);
    }

    public FaitBoolean(String nom, Boolean valeur)
    {
        this(nom, valeur, null, 0);
    }

    @Override
    public String toString()
    {
        return (getValeur() ? "" : "!") + getNom() + " (" + getNiveau() + ")";
    }

    @Override
    public FaitGenerique<Boolean> avecNiveau(int n)
    {
        return new FaitBoolean(this.getNom(), this.getValeur(),
                this.getQuestion(), n);
    }
}
