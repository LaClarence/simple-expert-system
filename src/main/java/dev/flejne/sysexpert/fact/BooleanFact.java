package dev.flejne.sysexpert.fact;

public final class BooleanFact extends GenericFact<Boolean> {

    private BooleanFact(
        String nom,
        Boolean valeur,
        String question,
        Integer niveau)
    {
        super(nom, valeur, question, niveau);
    }

    public BooleanFact(String nom, Boolean valeur, String question)
    {
        this(nom, valeur, question, 0);
    }

    public BooleanFact(String nom, Boolean valeur)
    {
        this(nom, valeur, null, 0);
    }

    @Override
    public String toString()
    {
        return (value() ? "" : "!") + name() + " (" + level() + ")";
    }

    @Override
    public Fact<Boolean> handleLevel(int n)
    {
        return new BooleanFact(this.name(), this.value(),
                this.questionMe(), n);
    }
}
