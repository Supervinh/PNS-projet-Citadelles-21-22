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
        CartePersonnage cibleNomPersonnage = cibleAleatoire(joueur);

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().equals(cibleNomPersonnage))
                .findFirst()
                .orElse(null);

        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        if (cible != null) {
            int montant = cible.getOr();
            boolean plurielle = montant > 1;
            joueur.ajouteOr(montant);
            cible.setOr(0);
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a volé " + CouleurConsole.printGold("" + montant) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("or") + " à " + cible.getNom());
        } else {
            String article;
            switch (" de " + cibleNomPersonnage.getArticle()) {
                case " de Le " -> article = " du ";
                case " de La " -> article = " de la ";
                case " de L'" -> article = " de l'";
                default -> article = " de " + cibleNomPersonnage.getArticle();
            }
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a essayé de voler les pièces d'" + CouleurConsole.printGold("or") + article + CouleurConsole.printRed(cibleNomPersonnage.getNom()));
        }
    }

    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.removeIf(c -> c.getNom().equals(joueur.getPersonnage().getNom()) || c.getNom().equals("Assassin") || this.estPersonnageMort(c));
        return cibles.get(new Random().nextInt(cibles.size()));
    }

    public boolean estPersonnageMort(CartePersonnage cp) {
        return MoteurDeJeu.joueurs.stream().filter(joueur -> joueur.getPersonnage().equals(cp)).anyMatch(Joueur::isEstTue);
    }

}
