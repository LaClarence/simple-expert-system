package dev.flejne.ia.sysexpert;

abstract class FaitUnit<T> implements Fait<T> {

    private final Integer niveau;
    private final String nom;
    private final String question;
    private final T valeur;

    protected FaitUnit(String nom, T valeur, String question, Integer niveau)
    {
        this.nom = nom;
        this.valeur = valeur;
        this.question = question;
        this.niveau = niveau;
    }
    
    public Integer getNiveau()
    {
        return this.niveau;
    }

    public String getNom()
    {
        return this.nom;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public boolean hasQuestion()
    {
        return this.question != null;
    }

    public T getValeur()
    {
        return this.valeur;
    }
 
}
