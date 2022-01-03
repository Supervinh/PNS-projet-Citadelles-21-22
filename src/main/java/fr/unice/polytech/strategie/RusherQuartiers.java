package fr.unice.polytech.strategie;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La stratégie qui construit au plus vite ses quartiers pour essayer de finir premier.
 */
public class RusherQuartiers implements IStrategie {

    /**
     * Choisit en priorité le personnage de l'architecte sinon il choisit aléatoirement.
     * Si il a moins de 3 pièces d'or il choisit en priorité le voleur sinon c'est aléatoire.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = personnages.stream().filter(cp -> cp.getNom().equals("Architecte")).findAny().orElseGet(
                () -> IStrategie.super.choixDePersonnage(joueur, personnages));
        if (joueur.getOr() < 3) {
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(choix);
        }
        return choix;
    }

    /**
     * On cible le personnage avec le plus de points sinon c'est aléatoire.
     *
     * @param joueur Le joueur qui joue.
     * @param ciblesTemp Les cibles de personnages.
     * @return La carte personnage ciblée.
     */
    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        Joueur cibleJoueur = MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElse(null);
        if (cibleJoueur != null) {
            return cibleJoueur.getStrategie().getIStrategie().choixDePersonnage(joueur, MoteurDeJeu.deck.getPersonnagesPossibles());
        } else {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
    }

    /**
     * Choisit le joueur avec le maximum de point sinon il en choisit un aléatoirement.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des joueurs.
     * @return Le joueur ciblé.
     */
    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
    }

    /**
     * Choisit un quartier qui rapportera le plus de point possible.
     *
     * @param joueur    Le joueur qui joue.
     * @param quartiers La liste des quartiers.
     * @return La carte quartier choisie.
     */
    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers);
        Collections.sort(carteQuartiers);
        return carteQuartiers.get(carteQuartiers.size() - 1);
    }

    /**
     * Donne le nom de la stratégie utilisée.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Quartiers les moins cher";
    }
}
