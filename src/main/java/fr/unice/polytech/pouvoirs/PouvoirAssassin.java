package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirAssassin implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.joueurs));
        cibles.remove(joueur);
        Joueur cible = cibles.get(new Random().nextInt(cibles.size()));

        System.out.println(joueur.getNom() + " a " + CouleurConsole.printRed("tué ") + cible.getNom());
        joueur.tue(cible);
    }
}
