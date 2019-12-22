package dev.flejne.sysexpert.fact;

abstract class GenericFact<T> implements Fact<T> {

    private final int level;
    private final String name;
    private final String question;
    private final T value;

    protected GenericFact(String nom, T valeur, String question, Integer niveau)
    {
        this.name = nom;
        this.value = valeur;
        this.question = question;
        this.level = niveau;
    }

    public int level()
    {
        return this.level;
    }

    public String name()
    {
        return this.name;
    }

    public String questionMe()
    {
        return this.question;
    }

    public boolean hasQuestion()
    {
        return this.question != null;
    }

    public T value()
    {
        return this.value;
    }
 
}
