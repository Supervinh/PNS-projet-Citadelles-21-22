package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Le pouvoir du condottière permet au personnage d'attaquer un quartier.
 */
public class PouvoirCondottiere implements IPouvoir {

    /**
     * On sélectionne une cible et on sélectionne un quartier de ce joueur à détruire, si on a choisi de détruire un quartier.
     * On récupère aussi la taxe si on a des quartiers militaires de contruit.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        Joueur cible = cibleAleatoire();
        CarteQuartier quartierDetruit = choixQuartierAleatoire(joueur, cible);
        System.out.println(CouleurConsole.printRed("| Pouvoir ") + joueur.getPersonnage().getNomColoured());
        if (quartierDetruit != null) {
            cible.getQuartiersConstruits().remove(quartierDetruit);
            joueur.ajouteOr(1 - quartierDetruit.getPrix());
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a détruit le quartier " + quartierDetruit.getNomColoured() + " de " + cible.getNomColoured());
            if (!cible.getQuartiersConstruits().contains(MoteurDeJeu.deck.getQuartiers)){
                MoteurDeJeu.deck.ajouterQuartierDeck(quartierDetruit);
                System.out.println("HAHAHAHAHHAHA");
            }
            else {
                if ((!cible.getPersonnage().getNom().equals("Condottiere")) && cible.getOr()>=1 && choixAction()){
                    cible.ajouteOr(-1);
                    cible.getQuartiers().add(quartierDetruit);
                    System.out.println(CouleurConsole.printRed("| ") + cible.getNomColoured() + " a récupéré le quartier " + quartierDetruit.getNomColoured());
                }
                else {
                    MoteurDeJeu.deck.ajouterQuartierDeck(quartierDetruit);
                    System.out.println(CouleurConsole.printRed("| ") + "pas de récupération de quartier ");
                }
            }
        } else {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " n'a pas détruit de quartier.");
        }
        System.out.println(CouleurConsole.printRed("|"));
        this.recupererTaxes(joueur);
    }

    /**
     * Sélectionne un joueur de manière aléatoire, mais pas l'évêque s'il est mort, pas si le nombre de quartiers construits est null, et pas si le joueur a fini le jeu en ayant contruit 8 quartiers.
     *
     * @return Retourne un joueur.
     */
    public Joueur cibleAleatoire() {
        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.personnagesConnus));
        cibles.removeIf(j -> (j.getPersonnage().getNom().equals("Évêque") && !j.isMort()) || j.getQuartiersConstruits().size() <= 0 || j.getQuartiersConstruits().size() >= MoteurDeJeu.quartiersAConstruire);
        return cibles.get(new Random().nextInt(cibles.size()));
    }

    /**
     * Choix d'un quartier à détruire aléatoirement.
     *
     * @param joueur Le joueur en question.
     * @param cible  La cible visée.
     * @return Retourne la carte quartier.
     */
    public CarteQuartier choixQuartierAleatoire(Joueur joueur, Joueur cible) {
        ArrayList<CarteQuartier> cibesPossible = new ArrayList<>(cible.getQuartiersConstruits().stream().filter(cq -> !cq.getNom().equals("Donjon") && hasEnoughMoney(joueur, cq)).toList());
        if (cibesPossible.size() > 0) {
            return cibesPossible.get(new Random().nextInt(cibesPossible.size()));
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

    public boolean choixAction(){
        Random r = new Random();
        return r.nextBoolean();
    }
}
