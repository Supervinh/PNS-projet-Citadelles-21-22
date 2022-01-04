package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Le pouvoir du voleur permet au personnage de voler l'or d'un autre personnage. Il ne peut pas voler l'assassin et
 * le personnage assassiné.
 */
public class PouvoirVoleur implements IPouvoir {

    /**
     * Si le personnage existe on récupère tout son or.
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

        Affichage.pouvoir(joueur);
        if (cible != null) {
            int montant = cible.getOr();
            boolean plurielle = montant > 1;
            joueur.ajouteOr(montant);
            cible.ajouteOr(-1 * cible.getOr());
            Affichage.volerOr(joueur, cible, montant, plurielle);
        } else {
            String article;
            switch (" de " + cibleNomPersonnage.getArticle()) {
                case " de Le " -> article = " du ";
                case " de La " -> article = " de la ";
                case " de L'" -> article = " de l'";
                default -> article = " de " + cibleNomPersonnage.getArticle();
            }
            Affichage.essayerVolerOr(joueur, article, cibleNomPersonnage);
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
        return joueur.getStrategie().getIStrategie().choixDeCibleCartePersonnage(joueur, cibles);
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
