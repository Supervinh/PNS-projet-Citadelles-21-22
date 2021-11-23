package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirAssassin implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        // Choix de Cible utilisant un nom de Personnage
        CartePersonnage cibleNomPersonnage = this.cibleAleatoire(joueur);

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().equals(cibleNomPersonnage))
                .findFirst()
                .orElse(null);

        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        if (cible != null) {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a " + CouleurConsole.printRed("tué " + cible.getNom()));
            joueur.tue(cible);
        } else {
            String article;
            switch (cibleNomPersonnage.getArticle()) {
                case "Le " -> article = "le ";
                case "La " -> article = "la ";
                case "L'" -> article = "l'";
                default -> article = cibleNomPersonnage.getArticle();
            }
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a essayé de " + CouleurConsole.printRed("tuer ") + article + CouleurConsole.printRed(cibleNomPersonnage.getNom()));
        }
    }

    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.remove(joueur.getPersonnage());
        return cibles.get(new Random().nextInt(cibles.size()));
    }
}
