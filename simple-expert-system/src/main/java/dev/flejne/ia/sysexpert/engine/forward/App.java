package dev.flejne.ia.sysexpert.engine.forward;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class App implements Ihm {

    private static final List<String> regles = Arrays.asList(
            "R1:  IF (Ordre=3(Quel est l'ordre?)) THEN Triangle",
            "R2:  IF (Triangle AND Angle Droit(La figure a-t-elle au moins un angle droit?)) THEN Triangle Rectangle",
            "R3:  IF (Triangle Cotes Egaux=2(Combien la figure a-t-elle de côtés égaux?)) THEN Triangle Isocèle",
            "R4:  IF (Triangle Rectangle AND Triangle Isocèle) THEN Triangle Rectangle Isocèle",
            "R5:  IF (Triangle AND Triangle Cotes Egaux=3(Combien la figure a-t-elle de côtés égaux?)) THEN Triangle Equilateral",
            "R6:  IF (Ordre=4(Quel est l'ordre?)) THEN Quadrilatère",
            "R7:  IF (Quadrilatère AND Cotes Parallèles=2(Combien la figure a-t-elle de côtés parallèles entre eux - 0, 2 ou 4?)) THEN Trapeze",
            "R8:  IF (Quadrilatère AND Cotes Parallèles=4(Combien la figure a-t-elle de côtés parallèles entre eux - 0, 2 ou 4?)) THEN Parallélogramme",
            "R9:  IF (Parallélogramme AND Angle Droit(La figure a-t-elle au moins un angle droit?)) THEN Rectangle",
            "R10: IF (Parallélogramme AND Cotes Egaux=4(Combien la figure a-t-elle de côtés égaux?)) THEN Losange",
            "R11: IF (Rectangle AND Losange) THEN Carré");

    private static final String TRUE_REGEXP =  "[YyOo]|[Oo]ui|[Yy]es|[Tt]rue";
    
    public static void main(String[] args)
    {
        App app = new App();
        app.run();
    }

    private final Scanner scanner = new Scanner(System.in);

    private Comparator<? super Fait<?>> parNiveauDecroissant()
    {
        return (f1, f2) -> -f1.getNiveau().compareTo(f2.getNiveau());
    }

    @Override
    public void afficherFaits(List<Fait<?>> faits)
    {
        String afficheFaits = "Solution(s) trouvée(s) : \n";
        afficheFaits += faits.stream().filter(f -> f.getNiveau() != 0)
                .sorted(parNiveauDecroissant()).map(Fait::toString)
                .collect(Collectors.joining("\n"));
        System.out.println(afficheFaits);

    }

    @Override
    public void afficherRegles(List<Regle> regles)
    {
        regles.stream().forEach(System.out::println);
    }

    private <T> T demanderValeur(
        String question,
        Supplier<T> supplier,
        String errorMessage)
    {
        System.out.print("Question: "+question+" ");
        while (true) {
            try {
                return supplier.get();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.err.println(
                        errorMessage + " : " + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public boolean demanderValeurBoolean(String question)
    {
        return demanderValeur(question, () -> this.scanner.next().matches(TRUE_REGEXP),
                " A boolean is expected!");
    }

    @Override
    public int demanderValeurEntiere(String question)
    {
        return demanderValeur(question, () -> this.scanner.nextInt(),
                "An integer is expected!");
    }

    private void run()
    {
        System.out.println("** Création du moteur d'inférences **");
        MoteurInferences moteurInferences = new MoteurInferences(this);

        if (moteurInferences.ajouterRegles(regles)) {
            System.out.println("** Ajout des règles terminé. **");
            moteurInferences.resoudre();
            System.out.println("** Résolution terminés. **");
        } else {
            System.out.println("** Echec de l'ajout des règles! **");
        }

    }

}
