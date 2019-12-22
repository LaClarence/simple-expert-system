package dev.flejne.sysexpert.fact;

public final class IntegerFact extends GenericFact<Integer> {

    private IntegerFact(
        String nom,
        Integer valeur,
        String question,
        Integer niveau)
    {
        super(nom, valeur, question, niveau);
    }

    public IntegerFact(String nom, Integer valeur, String question)
    {
        this(nom, valeur, question, 0);
    }

    /**
     * Lorsque le fait est inferé il n'y a pas de question.
     * 
     * @param nom
     * @param valeur
     */
    public IntegerFact(String nom, Integer valeur)
    {
        this(nom, valeur, null, 0);
    }

    @Override
    public String toString()
    {
        return name() + "=" + value() + " (" + level() + ")";
    }

    @Override
    public Fact<Integer> handleLevel(int n)
    {
        return new IntegerFact(this.name(), this.value(),
                this.questionMe(), n);
    }
}
