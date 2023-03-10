package fr.unice.polytech.strategie;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La stratégie qui consiste à prendre les rôles les moins aggressifs et les moins visés.
 */
public class Opportuniste implements IStrategie {

    /**
     * Permet de faire le bon choix du personnage en fonction de ce que le joueur a dans sa main.
     * Le joueur prend en priorité le voleur.
     * Sinon s'il a au moins un quartier religieux alors il prend l'évêque.
     * S'il a plus de 1 pièce d'or alors il choisit le condottière.
     * Si un autre joueur a au moins 4 pièces d'or alors il prend le voleur.
     * Sinon il choisit aléatoirement.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte du personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        int quartierReligieux = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals("Religion")).count();
        CartePersonnage choix = null;
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        if (quartierReligieux > 0)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Évêque")).findAny().orElse(null);
        if (joueur.getOr() > 1 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Condottiere")).findAny().orElse(null);
        if (orMax > 4)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(null);
        if (choix == null) choix = IStrategie.super.choixDePersonnage(joueur, personnages);
        return choix;
    }

    /**
     * Permet de choisir la cible d'une carte personnage.
     * Si le joueur a choisi le voleur alors il cible l'architecte ou le marchand.
     * Sinon il va cibler aléatoirement.
     *
     * @param joueur     Le joueur qui joue.
     * @param ciblesTemp Les cibles de personnages.
     * @return La carte personnage ciblée.
     */
    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        CartePersonnage personnageCible = null;
        if (joueur.getPersonnage().getNom().equals("Voleur")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Marchand")).findFirst().orElse(null);
        }
        if (personnageCible == null) {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
        return personnageCible;
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
     * Choisit un quartier religieux sinon un quartier qui est le moins cher possible.
     *
     * @param joueur    Le joueur qui joue.
     * @param quartiers La liste des quartiers.
     * @return La carte quartier choisie.
     */
    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers);
        Collections.sort(carteQuartiers);
        for (CarteQuartier q : carteQuartiers) {
            if (q.getGemme().equals("Religion")) {
                carteQuartiers.removeIf(qu -> !qu.getGemme().equals("Religion"));
                return carteQuartiers.get(carteQuartiers.size() - 1);
            }
        }
        return carteQuartiers.get(carteQuartiers.size() - 1);
    }

    /**
     * Donne le nom de la stratégie utilisée.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Opportuniste";
    }
}
