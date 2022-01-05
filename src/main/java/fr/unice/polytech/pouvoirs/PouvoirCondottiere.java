package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Le pouvoir du condottière permet au personnage d'attaquer un quartier.
 */
public class PouvoirCondottiere implements IPouvoir {

    /**
     * On sélectionne une cible et on sélectionne un quartier de ce joueur à détruire, si on a choisi de détruire un quartier.
     * On récupère aussi la taxe si on a des quartiers militaires de construit.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        Joueur cible = cibleAleatoire(joueur);
        if (cible != null) {
            CarteQuartier quartierDetruit = choixQuartierAleatoire(joueur, cible);
            Affichage.pouvoir(joueur);
            if (quartierDetruit != null) {
                cible.getQuartiersConstruits().remove(quartierDetruit);
                joueur.ajouteOr(1 - quartierDetruit.getPrix());
                CarteQuartier cimetiere = MoteurDeJeu.deck.getQuartiersPossibles().stream().filter(quartier -> quartier.getNom().equals("Cimetière")).findFirst().orElse(null);
                Affichage.detructionQuatier(joueur, quartierDetruit, cible);
                if (!cible.getQuartiersConstruits().contains(cimetiere)) {
                    MoteurDeJeu.deck.ajouterQuartierDeck(quartierDetruit);
                } else {
                    if ((!cible.getPersonnage().getNom().equals("Condottiere")) && cible.getOr() >= 1 && choixAction()) {
                        cible.ajouteOr(-1);
                        cible.getQuartiers().add(quartierDetruit);
                        Affichage.recupererQuartier(cible, quartierDetruit);
                    } else {
                        MoteurDeJeu.deck.ajouterQuartierDeck(quartierDetruit);
                        Affichage.pasRecupererQuartier();
                    }
                }
            } else {
                Affichage.pasDetruitQuartier(joueur);
            }
            Affichage.barreRouge();
        }
        this.recupererTaxes(joueur);
    }

    /**
     * Sélectionne un joueur de manière aléatoire, mais pas l'évêque s'il est mort, pas si le nombre de quartiers construits est null, et pas si le joueur a fini le jeu en ayant construit 8 quartiers.
     *
     * @return Retourne un joueur.
     */
    public Joueur cibleAleatoire(Joueur joueur) {
        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.personnagesConnus));
        cibles.removeIf(j -> (j.getPersonnage().getNom().equals("Évêque") && !j.isMort()) || j.getQuartiersConstruits().size() <= 0 || j.getQuartiersConstruits().size() >= MoteurDeJeu.quartiersAConstruire);
        if (cibles.size() > 0) {
            return joueur.getStrategie().getIStrategie().choixDeCibleJoueur(joueur, cibles);
        } else {
            return null;
        }
    }

    /**
     * Choix d'un quartier à détruire aléatoirement.
     *
     * @param joueur Le joueur en question.
     * @param cible  La cible visée.
     * @return Retourne la carte quartier.
     */
    public CarteQuartier choixQuartierAleatoire(Joueur joueur, Joueur cible) {
        ArrayList<CarteQuartier> ciblesPossible = new ArrayList<>(cible.getQuartiersConstruits().stream().filter(cq -> !cq.getNom().equals("Donjon") && hasEnoughMoney(joueur, cq)).toList());
        if (ciblesPossible.size() > 0) {
            return ciblesPossible.get(new Random().nextInt(ciblesPossible.size()));
        } else {
            return null;
        }
    }

    /**
     * Savoir si oui ou non un joueur peux ou pas construire un quartier.
     *
     * @param joueur   Le joueur en question.
     * @param quartier La cible visée.
     * @return Si oui ou non le joueur peux construire.
     */
    public boolean hasEnoughMoney(Joueur joueur, CarteQuartier quartier) {
        return (joueur.getOr() >= quartier.getPrix() - 1);
    }

    public boolean choixAction() {
        Random r = new Random();
        return r.nextBoolean();
    }
}
