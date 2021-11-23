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
        cibles.removeIf(c -> c.getNom().equals(joueur.getPersonnage().getNom()) || c.getNom().equals("Assassin") || this.estPersonnageMort(c));
        CartePersonnage cibleNomPersonnage = cibles.get(new Random().nextInt(cibles.size()));

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().equals(cibleNomPersonnage))
                .findFirst()
                .orElse(null);

        if (cible != null) {
            boolean plurielle = cible.getOr() > 1;
            joueur.ajouteOr(cible.getOr());
            cible.setOr(0);
            System.out.println(joueur.getNom() + " a volé " + CouleurConsole.printGold("" + cible.getOr()) + " pièce" + (plurielle ? "s" : "") + " d'or à " + cible.getNom());
        } else {
            System.out.println(joueur.getNom() + " a essayé de voler les pièces d'or de " + cibleNomPersonnage.getNom());
        }
    }

    public boolean estPersonnageMort(CartePersonnage cp) {
        return MoteurDeJeu.joueurs.stream().filter(joueur -> joueur.getPersonnage().equals(cp)).anyMatch(Joueur::isEstTue);
    }

}
