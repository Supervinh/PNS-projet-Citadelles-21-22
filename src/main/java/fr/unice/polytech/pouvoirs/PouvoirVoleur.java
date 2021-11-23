package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirVoleur implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.joueurs));
        cibles.remove(joueur);
        Joueur cible = cibles.get(new Random().nextInt(cibles.size()));

        while (cible.getPersonnage().getNom().equals("Assassin") || cible.isEstTue()) {
            cible = cibles.get(new Random().nextInt(cibles.size()));
        }

        joueur.ajouteOr(cible.getOr());

        System.out.println(joueur.getNom() + " a voler " + CouleurConsole.printGold(String.valueOf(cible.getOr())) + " à " + cible.getNom());

        cible.setOr(0);
    }
}
