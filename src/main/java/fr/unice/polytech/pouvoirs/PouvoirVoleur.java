package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirVoleur implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        // Choix de Cible utilisant un nom de Personnage
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.remove(joueur.getPersonnage());
        cibles.removeIf(p-> p.getNom().equals("Assassin"));
        CartePersonnage cibleNomPersonnage = cibles.get(new Random().nextInt(cibles.size()));

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().equals(cibleNomPersonnage))
                .findFirst()
                .orElse(null);



        if (cible != null && !cible.isEstTue()) {
            joueur.ajouteOr(cible.getOr());

            System.out.println(joueur.getNom() + " a volé " + CouleurConsole.printGold(String.valueOf(cible.getOr())) + " pièces d'or à " + cible.getNom());

            cible.setOr(0);
        } else {
            System.out.println(joueur.getNom() + " a essayé de voler des pièces d'or à " + cibleNomPersonnage.getNom());
        }

    }
}
