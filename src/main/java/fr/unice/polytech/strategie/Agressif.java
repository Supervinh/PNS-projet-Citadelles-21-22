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
     * Permet de faire le bon choix du personnage en fonction de ce que le joueur a dans sa main.
     * Choisit en priorité l'assassin.
     * Si le nombre de quartiers construits est supérieur à 5 on prend le condottière.
     * Si on n'a pas de quartiers en main on prend le magicien.
     * Si un autre joueur à plus de 7 pièces d'or on prend le voleur.
     * Sinon on prend un personnage aléatoire.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte du personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix;
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        int nbreQuartiersConstruits = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiersConstruits).mapToInt(ArrayList::size).max().orElse(0);
        choix = personnages.stream().filter(cp -> cp.getNom().equals("Assassin")).findAny().orElse(null);
        if (nbreQuartiersConstruits>5 || choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Condottiere")).findAny().orElse(null);
        if (joueur.getQuartiers().size()==0 || choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Magicien")).findAny().orElse(null);
        if (orMax>7 || choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(null);
        if (choix == null) choix = IStrategie.super.choixDePersonnage(joueur, personnages);
        return choix;
    }

    /**
     * Permet de choisir la cible d'une carte personnage.
     * Si on a pioche l'assassin, on va cibler en fonction de ce que les autres joueurs ont.
     * Si quelqu'un a plus de 4 cartes en main, et 4 quartiers construits ou plus de 6 cartes en main, on va viser le condottière.
     * Sinon si quelqu'un a plus de 5 carte en main, on va viser le roi.
     * Sinon si quelqu'un a plus de 6 pièces d'or, on va viser le voleur.
     * Si quelqu'un a plus de 3 pièces d'or ou 4 quartiers construits alors on vise l'architecte.
     * Sinon si le joueur n'a pas pioché l'assassin, mais le voleur, on va viser l'architecte.
     * Et sinon le joueur va viser quelqu'un aléatoirement.
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
        return "Griefer";
    }
}
