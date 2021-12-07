package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirMagicien implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        // Choix de Cible utilisant un nom de Personnage
        CartePersonnage cibleNomPersonnage = this.cibleAleatoire(joueur);

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().getNom().equals(cibleNomPersonnage.getNom()))
                .findFirst()
                .orElse(null);

        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        if (cible != null) {
            ArrayList<CarteQuartier> temporaire = new ArrayList<>(List.copyOf(joueur.getQuartiers()));
            joueur.setQuartiers(cible.getQuartiers());
            cible.setQuartiers(temporaire);

            System.out.println(CouleurConsole.printRed("| ") +joueur.getNom() + " a échangé ses cartes avec " + cible.getNom());
        } else {
            System.out.println(CouleurConsole.printRed("| ") +joueur.getNom() + " a essayé d'échanger ses cartes avec " + cibleNomPersonnage.getArticle().toLowerCase() + CouleurConsole.printRed(cibleNomPersonnage.getNom()));
        }
    }

    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.remove(joueur.getPersonnage());
        return cibles.get(new Random().nextInt(cibles.size()));
    }
}
