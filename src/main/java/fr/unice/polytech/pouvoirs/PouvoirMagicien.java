package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirMagicien implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printRed("| ") + CouleurConsole.tiret() + "Choix 1: échanger ses cartes avec un joueur \n" + CouleurConsole.printRed("| ") + CouleurConsole.tiret() + "Choix 2: échanger n cartes avec la pioche\n" + CouleurConsole.printRed("|"));
        choixAction(joueur);
    }

    public void echangerCartesAvecJoueur(Joueur joueur) {
        CartePersonnage cibleNomPersonnage = this.cibleAleatoire(joueur);
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().getNom().equals(cibleNomPersonnage.getNom()))
                .findFirst()
                .orElse(null);
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a choisi d'échanger ses cartes avec un joueur");
        if (cible != null) {
            ArrayList<CarteQuartier> temporaire = new ArrayList<>(List.copyOf(joueur.getQuartiers()));
            joueur.setQuartiers(cible.getQuartiers());
            cible.setQuartiers(temporaire);

            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a échangé ses cartes avec " + cible.getNomColoured());
        } else {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a essayé d'échanger ses cartes avec " + cibleNomPersonnage.getArticle().toLowerCase() + cibleNomPersonnage.getNomColoured());
        }
    }

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

    public void choixAction(Joueur joueur) {
        Random r = new Random();
        int i = r.nextInt(2);
        int nb = 0;
        if (joueur.getQuartiers().size() > 0) {
            nb = r.nextInt(joueur.getQuartiers().size());
        }
        if (i == 0) {
            echangerCartesAvecPioche(joueur, nb);
        } else {
            echangerCartesAvecJoueur(joueur);
        }
    }

    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<Joueur> cibles = new ArrayList<>(MoteurDeJeu.joueurs);
        cibles.remove(joueur);
        return cibles.get(new Random().nextInt(cibles.size())).getPersonnage();
    }
}
