package dev.flejne.sysexpert.fact;

import dev.flejne.sysexpert.app.UserInterface;

public final class Factory {
    private static Fact<?> construitFaitBoolean(
        final String value)
    {
        boolean isTrue = !value.contains("!");
        String faitBoolean = isTrue ? value : value.substring(1).trim();
        faitBoolean = faitBoolean.replaceFirst("^\\(", "");
        String[] nomQuestion = faitBoolean.split("[()]");
        int nbTerms = nomQuestion.length;
        if (nbTerms >= 1) {
            String question = (nbTerms == 2) ? nomQuestion[1].trim() : null;
            return new BooleanFact(nomQuestion[0].trim(), isTrue, question);
        }
        throw new IllegalArgumentException(
                "La chaine du fait booleen est invalide!");
    }

    private static Fact<?> construitFaitEntier(
        final String value)
    {
        String faitEntier = value.replaceFirst("^\\(", "");
        String[] nomValeurQuestion = faitEntier.split("[=()]");
        int nbTerms = nomValeurQuestion.length;
        if (nbTerms >= 2) {
            String question = (nbTerms == 3) ? nomValeurQuestion[2].trim()
                    : null;
            Integer entier = Integer.decode(nomValeurQuestion[1]);
            return new IntegerFact(nomValeurQuestion[0].trim(), entier,
                    question);

        }
        throw new IllegalArgumentException(
                "La chaine du fait entier est invalide!");
    }

    public static Fact<?> fromQuestion(
        Fact<?> faitAvecQuestion,
        UserInterface cli)
    {
        String nom = faitAvecQuestion.name();
        String question = faitAvecQuestion.questionMe();
        if (faitAvecQuestion instanceof IntegerFact) {
            int valeur = cli.demanderValeurEntiere(question);
            return new IntegerFact(nom, valeur);
        } else if (faitAvecQuestion instanceof BooleanFact) {
            boolean valeur = cli.demanderValeurBoolean(question);
            return new BooleanFact(nom, valeur);
        }
        throw new IllegalArgumentException("Type de fait non gere!");
    }

    public static Fact<? extends Object> fromString(final String value)
    {
        String fait = value.trim();
        return fait.contains("=") ? construitFaitEntier(fait)
                : construitFaitBoolean(fait);
    }

    private Factory()
    {
    }
}
