package dev.flejne.ia.expert.genericexpertsystem;

public final class FaitFactory {
    private static FaitGenerique<? extends Object> construitFaitBoolean(
        final String value)
    {
        boolean isTrue = !value.contains("!");
        String faitBoolean = isTrue ? value : value.substring(1).trim();
        faitBoolean = faitBoolean.replaceFirst("^\\(", "");
        String[] nomQuestion = faitBoolean.split("[()]");
        int nbTerms = nomQuestion.length;
        if (nbTerms >= 1) {
            String question = (nbTerms == 2) ? nomQuestion[1].trim() : null;
            return new FaitBoolean(nomQuestion[0].trim(), isTrue, question);
        }
        throw new IllegalArgumentException(
                "La chaine du fait booleen est invalide!");
    }

    private static FaitGenerique<? extends Object> construitFaitEntier(
        final String value)
    {
        String faitEntier = value.replaceFirst("^\\(", "");
        String[] nomValeurQuestion = faitEntier.split("[=()]");
        int nbTerms = nomValeurQuestion.length;
        if (nbTerms >= 2) {
            String question = (nbTerms == 3) ? nomValeurQuestion[2].trim()
                    : null;
            Integer entier = Integer.decode(nomValeurQuestion[1]);
            return new FaitEntier(nomValeurQuestion[0].trim(), entier,
                    question);

        }
        throw new IllegalArgumentException(
                "La chaine du fait entier est invalide!");
    }

    static FaitGenerique<?> fromQuestion(
        FaitGenerique<?> faitAvecQuestion,
        MoteurInferences m)
    {
        String nom = faitAvecQuestion.getNom();
        String question = faitAvecQuestion.getQuestion();
        if (faitAvecQuestion instanceof FaitEntier) {
            int valeur = m.demanderValeurEntiere(question);
            return new FaitEntier(nom, valeur);
        } else if (faitAvecQuestion instanceof FaitBoolean) {
            boolean valeur = m.demanderValeurBoolean(question);
            return new FaitBoolean(nom, valeur);
        }
        throw new IllegalArgumentException("Type de fait non gere!");
    }

    static FaitGenerique<? extends Object> fromString(final String value)
    {
        String fait = value.trim();
        return fait.contains("=") ? construitFaitEntier(fait)
                : construitFaitBoolean(fait);
    }

    private FaitFactory()
    {
    }
}
