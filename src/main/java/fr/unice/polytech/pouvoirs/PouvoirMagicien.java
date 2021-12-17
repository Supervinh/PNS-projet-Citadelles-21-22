package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Le pouvoir du magicien permet au personnage d'échanger des cartes avec un joueur ou avec la pioche.
 */
public class PouvoirMagicien implements IPouvoir {

    /**
     * L'utilisation du pouvoir, on choisit l'action que l'on fait.
     *
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printRed("| ") + CouleurConsole.tiret() + "Choix 1: échanger ses cartes avec un joueur \n" + CouleurConsole.printRed("| ") + CouleurConsole.tiret() + "Choix 2: échanger n cartes avec la pioche\n" + CouleurConsole.printRed("|"));
        choixAction(joueur, new Random().nextBoolean());
    }

    /**
     * On échange nos cartes avec un joueur. On choisit une cible aléatoirement et on échange toutes nos cartes avec lui.
     *
     * @param joueur Le joueur en question.
     */
    public void echangerCartesAvecJoueur(Joueur joueur) {
        CartePersonnage cibleNomPersonnage = this.cibleAleatoire(joueur);
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().getNom().equals(cibleNomPersonnage.getNom()))
                .findFirst()
                .orElse(null);
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a choisi d'échanger ses cartes avec un joueur");
        ArrayList<CarteQuartier> temporaire = new ArrayList<>(List.copyOf(joueur.getQuartiers()));
        if (cible != null) {
            joueur.setQuartiers(cible.getQuartiers());
            cible.setQuartiers(temporaire);
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a échangé ses cartes avec " + cible.getNomColoured());
        }
    }

    /**
     * On échange des cartes avec la pioche. Si on entre une valeur négative ça reviens à mettre 0, si on entre une valeur trop grande on met le
     * maximum des cartes dans la main. Puis on échange le nombre définit de cartes avec la pioche et les cartes échangées donc remise dans la pile.
     *
     * @param joueur Le joueur en question.
     * @param nb     Le nombre de cartes à échanger.
     */
    public void echangerCartesAvecPioche(Joueur joueur, int nb) {
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a choisi d'échanger des cartes avec la pioche");
        if (nb > joueur.getQuartiers().size()) {
            nb = joueur.getQuartiers().size();
        }
        if (nb < 0) {
            nb = 0;
        }
        for (int i = 0; i < nb; i++) {
            System.out.print(CouleurConsole.printRed("| "));
            MoteurDeJeu.deck.ajouterQuartierDeck(joueur.getQuartiers().get(i));
            joueur.getQuartiers().set(i, joueur.piocherQuartier());
        }
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a échangé " + CouleurConsole.printPurple("" + nb) + " cartes avec la pioche.");
    }

    /**
     * On choisit aléatoirement si on échange ses cartes avec un joueur ou avec la pioche.
     *
     * @param joueur Le joueur en question.
     * @param b      Un boolean qui permet de faire le choix.
     */
    public void choixAction(Joueur joueur, boolean b) {
        int nb = choixNombreQuartiers(joueur);
        if (b) {
            echangerCartesAvecPioche(joueur, nb);
        } else {
            echangerCartesAvecJoueur(joueur);
        }
    }

    /**
     * On retourne le nombre de cartes que nous allons prendre ou échanger.
     *
     * @param joueur Le joueur en question.
     * @return Le nombre de quartiers.
     */
    public int choixNombreQuartiers(Joueur joueur) {
        Random r = new Random();
        if (joueur.getQuartiers().size() > 0) {
            return r.nextInt(joueur.getQuartiers().size());
        }
        return 0;
    }

    /**
     * Sélectionne un personnage de manière aléatoire, mais pas lui-même.
     *
     * @param joueur Le joueur en question.
     * @return Retourne une carte de personnage.
     */
    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<Joueur> cibles = new ArrayList<>(MoteurDeJeu.joueurs);
        cibles.remove(joueur);
        return cibles.get(new Random().nextInt(cibles.size())).getPersonnage();
    }
}
