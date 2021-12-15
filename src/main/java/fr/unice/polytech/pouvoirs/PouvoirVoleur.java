package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Le pouvoir du voleur permet au personnage de voler l'or d'un autre personnage. Il ne peux pas voler l'assassin et
 * le personnage assassiné.
 */
public class PouvoirVoleur implements IPouvoir {

    /**
     * Si le personnage existe on récupère toute son or.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        CartePersonnage cibleNomPersonnage = cibleAleatoire(joueur);
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().equals(cibleNomPersonnage))
                .findFirst()
                .orElse(null);

        System.out.println(CouleurConsole.printRed("| Pouvoir ") + joueur.getPersonnage().getNomColoured());
        if (cible != null) {
            int montant = cible.getOr();
            boolean plurielle = montant > 1;
            joueur.ajouteOr(montant);
            cible.ajouteOr(-1 * cible.getOr());
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a volé " + CouleurConsole.printGold("" + montant) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("Or") + " à " + cible.getNomColoured());
        } else {
            String article;
            switch (" de " + cibleNomPersonnage.getArticle()) {
                case " de Le " -> article = " du ";
                case " de La " -> article = " de la ";
                case " de L'" -> article = " de l'";
                default -> article = " de " + cibleNomPersonnage.getArticle();
            }
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a essayé de voler les pièces d'" + CouleurConsole.printGold("Or") + article + cibleNomPersonnage.getNomColoured());
        }
    }

    /**
     * Sélectionne un personnage de manière aléatoire, mais pas lui-même, ni l'assassin, ni le personnage mort.
     *
     * @param joueur Le joueur en question.
     * @return Retourne une carte personnage.
     */
    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.removeIf(c -> c.getNom().equals(joueur.getPersonnage().getNom()) || c.getNom().equals("Assassin") || this.estPersonnageMort(c));
        return cibles.get(new Random().nextInt(cibles.size()));
    }

    /**
     * On vérifie si le personnage est mort.
     *
     * @param cp Le personnage en question.
     * @return Retourne un booléen.
     */
    public boolean estPersonnageMort(CartePersonnage cp) {
        return MoteurDeJeu.joueurs.stream().filter(joueur -> joueur.getPersonnage().equals(cp)).anyMatch(Joueur::isMort);
    }

}
