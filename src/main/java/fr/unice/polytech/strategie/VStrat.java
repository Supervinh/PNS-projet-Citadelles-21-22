package fr.unice.polytech.strategie;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;

public class VStrat implements IStrategie {

    /**
     * Permet de faire le bon choix du personnage en fonction de ce que le joueur a dans sa main.
     * On calcule le nombre de quartiers religion que le joueur a construit.
     * Si le joueur n'a pas de quartiers en main, il va choisir le magicien.
     * Si le joueur a plus de 2 quartiers en main, qu'il a plus de 5 pièces d'or, qu'il a plus de 4 quartiers construits et que
     * ce n'est pas le roi alors il prend le roi.
     * Si le joueur a plus de 2 quartiers en main et qu'il a plus de 5 pièces d'or alors il prend architecte.
     * Si un autre joueur a plus de 4 pièces d'or alors il prend voleur.
     * Si le joueur a plus d'un quartier religieux, il prend l'évêque.
     * Sinon il prend le marchand.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte du personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = null;
        int quartierReligieux = 0;
        for (int i = 0; i < joueur.getQuartiersConstruits().size(); i++) {
            if (joueur.getQuartiersConstruits().get(i).getGemme().equals("Religion")) quartierReligieux++;
        }
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        if (joueur.getQuartiers().size() == 0)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Magicien")).findAny().orElse(null);
        if (joueur.getQuartiers().size() > 2 && joueur.getOr() > 5 && joueur.getQuartiersConstruits().size()>4 && !joueur.isRoi() && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Roi")).findAny().orElse(null);
        if (joueur.getQuartiers().size() > 2 && joueur.getOr() > 5 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Architecte")).findAny().orElse(null);
        if (orMax > 4 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(null);
        if (quartierReligieux > 1 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Évêque")).findAny().orElse(null);
        if (choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Marchand")).findAny().orElseGet(() -> IStrategie.super.choixDePersonnage(joueur, personnages));
        return choix;
    }

    /**
     * Permet de choisir la cible d'une carte personnage.
     * Si le joueur a choisi l'assassin alors il va cibler l'architecte ou le magicien.
     * Sinon s'il a choisi le voleur alors il va cibler l'architecte ou le marchand.
     * Sinon il choisit aléatoirement.
     *
     * @param joueur Le joueur qui joue.
     * @param ciblesTemp Les cibles de personnages.
     * @return La carte personnage ciblée.
     */
    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        CartePersonnage personnageCible = null;
        if (joueur.getPersonnage().getNom().equals("Assassin")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Magicien")).findFirst().orElse(null);
        } else if (joueur.getPersonnage().getNom().equals("Voleur")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Marchand")).findFirst().orElse(null);
        }
        if (personnageCible == null) {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
        return personnageCible;
    }

    /**
     * Si le joueur a choisi le magicien alors il cible le joueur avec le plus de cartes quartiers en mains.
     * Sinon il vise juste la personne avec le plus haut score.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des joueurs.
     * @return Le joueur ciblé.
     */
    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        if (joueur.getPersonnage().getNom().equals("Magicien")) {
            int nbreQuartierMain = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiers).mapToInt(ArrayList::size).max().orElse(0);
            return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == nbreQuartierMain).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
        }
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
    }

    /**
     * Choisit un quartier qui est le moins cher possible.
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
        return "SpécialVinh";
    }
}
