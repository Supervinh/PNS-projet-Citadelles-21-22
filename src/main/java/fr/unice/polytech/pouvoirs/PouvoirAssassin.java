package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirAssassin implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        CartePersonnage cibleNomPersonnage = cibleAleatoire(joueur);
        Joueur cible = cibleExistante(cibleNomPersonnage);
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
        if (cible != null) {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a " + CouleurConsole.printRed("tué " + cible.getNomColoured()));
            this.tue(cible);
        } else {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a essayé de " + CouleurConsole.printRed("tuer ") + cibleNomPersonnage.getArticle().toLowerCase() + cibleNomPersonnage.getNomColoured());
        }
    }

    public Joueur cibleExistante(CartePersonnage cibleNomPersonnage) {
        return MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().getNom().equals(cibleNomPersonnage.getNom()))
                .findFirst()
                .orElse(null);
    }

    public CartePersonnage cibleAleatoire(Joueur joueur) {
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.remove(joueur.getPersonnage());
        return cibles.get(new Random().nextInt(cibles.size()));
    }

    public void tue(Joueur joueur) {
        joueur.setMort(true);
        if (joueur.getPersonnage().getNom().equals("Roi")) joueur.setRoi(false);
    }
}
