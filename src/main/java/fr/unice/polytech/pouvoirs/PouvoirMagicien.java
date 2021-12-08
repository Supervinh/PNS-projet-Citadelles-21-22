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
     * @param joueur Le joueur en question.
     */
    @Override
    public void utiliserPouvoir(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printRed("| ") + CouleurConsole.tiret() + "Choix 1: échanger ses cartes avec un joueur \n" + CouleurConsole.printRed("| ") + CouleurConsole.tiret() + "Choix 2: échanger n cartes avec la pioche\n" + CouleurConsole.printRed("|"));
        choixAction(joueur,choisit());
    }

    /**
     * On échange nos cartes avec un joueur. On choisit une cible aléatoirement et on échange toutes nos cartes avec lui.
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
        joueur.setQuartiers(cible.getQuartiers());
        cible.setQuartiers(temporaire);
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a échangé ses cartes avec " + cible.getNomColoured());
    }

    /**
     * On échange des cartes avec la pioche. Si on entre une valeur négative ça reviens à mettre 0, si on entre une valeur trop grande on met le
     * maximum des cartes dans la main. Puis on échange le nombre définit de cartes avec la pioche et les cartes échangées donc remis dans la pile.
     * @param joueur Le joueur en question.
     * @param nb Le nombre de cartes à échanger.
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
     * Un chiffre aléatoire entre 0 et 1.
     * @return Retourne 0 ou 1.
     */
    public int choisit(){
        Random r=new Random();
        int i=r.nextInt(2);
        return i;
    }

    /**
     * On choisit aléatoirement si on échange ses cartes avec un joueur ou avec la pioche.
     * @param joueur Le joueur en question.
     * @param i Un chiffre entre 0 et 1 qui permet de faire le choix.
     */
    public void choixAction(Joueur joueur, int i){
        Random r=new Random();
        int nb=0;
        if(joueur.getQuartiers().size()>0){nb=r.nextInt(joueur.getQuartiers().size());}
        if(i==0){echangerCartesAvecPioche(joueur,nb);}
        else{
            echangerCartesAvecJoueur(joueur);
        }
    }

    /**
     * Sélectionne un personnage de manière aléatoire mais pas lui même.
     * @param joueur Le joueur en question.
     * @return Retourne une carte de personnage.
     */
    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<Joueur> cibles = new ArrayList<>(MoteurDeJeu.joueurs);
        cibles.remove(joueur);
        return cibles.get(new Random().nextInt(cibles.size())).getPersonnage();
    }
}
