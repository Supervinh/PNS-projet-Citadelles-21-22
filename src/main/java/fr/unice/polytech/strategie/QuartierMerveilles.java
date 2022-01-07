package fr.unice.polytech.strategie;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.Joueur;

import java.util.ArrayList;

/**
 * La stratégie qui construit les quartiers dites merveilles.
 */
public class QuartierMerveilles implements IStrategie {

    /**
     * Choisit un personnage aléatoire.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte du personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        return IStrategie.super.choixDePersonnage(joueur, personnages);
    }

    /**
     * Choisit un personnage a cibler.
     *
     * @param joueur Le joueur qui joue.
     * @param ciblesTemp Les cibles de personnages.
     * @return Le personnage visé.
     */
    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
    }

    /**
     * Choisit le joueur a cibler.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des joueurs.
     * @return Le joueur ciblé
     */
    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        return IStrategie.super.choixDeCibleJoueur(joueur, cibles);
    }

    /**
     * Choisit un quartier de préférence avec une gemme prestige.
     *
     * @param joueur    Le joueur qui joue.
     * @param quartiers La liste des quartiers.
     * @return La carte quartier choisie.
     */
    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers.stream().filter(cq -> cq.getGemme().equals("Prestige")).toList());
        if (carteQuartiers.size() > 0) {
            return carteQuartiers.get(carteQuartiers.size() - 1);
        } else {
            return IStrategie.super.choixDeQuartier(joueur, quartiers);
        }
    }

    /**
     * Donne le nom de la stratégie utilisée.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Merveille";
    }
}
