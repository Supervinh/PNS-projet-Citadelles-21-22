package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Affichage;
import fr.unice.polytech.cartes.CarteQuartier;
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
        Affichage.pouvoir(joueur);
        Affichage.echangeCartePiocheOuJoueur();
        choixAction(joueur, new Random().nextBoolean());
    }

    /**
     * On échange nos cartes avec un joueur. On choisit une cible aléatoirement et on échange toutes nos cartes avec lui.
     *
     * @param joueur Le joueur en question.
     */
    public void echangerCartesAvecJoueur(Joueur joueur) {
        Joueur cible = this.cibleAleatoire(joueur);
        Affichage.choixEchangeJoueur(joueur);
        ArrayList<CarteQuartier> temporaire = new ArrayList<>(List.copyOf(joueur.getQuartiers()));
        if (cible != null) {
            joueur.setQuartiers(cible.getQuartiers());
            cible.setQuartiers(temporaire);
            Affichage.echangeAvecJoueur(joueur, cible);
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
        Affichage.choixEchangePioche(joueur);
        if (nb > joueur.getQuartiers().size()) {
            nb = joueur.getQuartiers().size();
        }
        if (nb < 0) {
            nb = 0;
        }
        for (int i = 0; i < nb; i++) {
            Affichage.barreRouge();
            MoteurDeJeu.deck.ajouterQuartierDeck(joueur.getQuartiers().get(i));
            joueur.getQuartiers().set(i, joueur.piocherQuartier());
        }
        Affichage.echangeAvecPioche(joueur, nb);
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
    public Joueur cibleAleatoire(Joueur joueur) {
        return joueur.getStrategie().getIStrategie().choixDeCibleJoueur(joueur, MoteurDeJeu.joueurs);
    }
}
