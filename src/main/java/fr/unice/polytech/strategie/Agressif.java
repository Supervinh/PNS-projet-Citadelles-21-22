package fr.unice.polytech.strategie;

import fr.unice.polytech.*;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La stratégie qui permet d'être aggressif envers les autres joueurs.
 */
public class Agressif implements IStrategie {

    /**
     * Choisit en priorité l'assassin pui voleur sinon c'est aléatoire.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte du personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix;
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        int nbreQuartierMain = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiers).mapToInt(ArrayList::size).max().orElse(0);
        choix = personnages.stream().filter(cp -> cp.getNom().equals("Assassin")).findAny().orElse(null);
        if (nbreQuartierMain>5 || choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Condottiere")).findAny().orElse(null);
        if (joueur.getQuartiers().size()==0 || choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Magicien")).findAny().orElse(null);
        if (orMax>7 || choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(null);
        if (choix == null) choix = IStrategie.super.choixDePersonnage(joueur, personnages);
        return choix;
    }

    /**
     * Si on a pioche l'assassin, on va ciblé l'architecte ou le magicien.
     * Si on a le voleur, on va ciblé l'architecte ou le marchand.
     * Et sinon on vise aléatoirement un personnage.
     *
     * @param joueur Le joueur qui joue.
     * @return La carte personnage ciblée.
     */
    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        CartePersonnage personnageCible = null;
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        int nbreQuartierConstruit = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiersConstruits).mapToInt(ArrayList::size).max().orElse(0);
        int nbreQuartierMain = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiers).mapToInt(ArrayList::size).max().orElse(0);
        if (joueur.getPersonnage().getNom().equals("Assassin")) {
            if ((nbreQuartierMain>4 && nbreQuartierMain==joueur.getQuartiersConstruits().size()) || nbreQuartierMain>6) personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Condottiere")).findFirst().orElse(null);
            else if (nbreQuartierMain>5) personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Roi")).findFirst().orElse(null);
            else if (orMax>6)personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Voleur")).findFirst().orElse(null);
            if (orMax>3 || nbreQuartierConstruit>4) personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte")).findFirst().orElse(null);
        } else if (joueur.getPersonnage().getNom().equals("Voleur")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Marchand")).findFirst().orElse(null);
        }
        if (personnageCible == null) {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
        return personnageCible;
    }

    /**
     * Choisit le joueur avec le plus de quartiers en main.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des joueurs.
     * @return Le joueur ciblé.
     */
    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        int nbreQuartierMain = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiers).mapToInt(ArrayList::size).max().orElse(0);
        if (nbreQuartierMain > 4) {
            return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == nbreQuartierMain).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
        }
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
    }

    /**
     * Choisit un quartier qui est le moins cher.
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
        return "Stratégie agressive afin de gêner au plus les adversaires";
    }
}
