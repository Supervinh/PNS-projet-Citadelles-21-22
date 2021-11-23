package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.*;

public class PouvoirAssassin implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        // Choix de Cible utilisant un nom de Personnage
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.remove(joueur.getPersonnage());
        CartePersonnage cibleNomPersonnage = cibles.get(new Random().nextInt(cibles.size()));

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream().filter(j -> j.getPersonnage().equals(cibleNomPersonnage)).findFirst().orElse(null);
        if (cible != null) {
            System.out.println(joueur.getNom() + " a " + CouleurConsole.printRed("tué " + cible.getNom()));
            joueur.tue(cible);
        } else {
            System.out.println(joueur.getNom() + " a essayé de " + CouleurConsole.printRed("tuer ") + cibleNomPersonnage.getArticle() + cibleNomPersonnage.getNom());
        }
    }
}
