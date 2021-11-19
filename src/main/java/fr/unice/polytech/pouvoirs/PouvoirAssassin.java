package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirAssassin implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur, Joueur cible) {
        System.out.println(joueur.getNom() + " a " + CouleurConsole.RED + "tué " + CouleurConsole.RESET + cible.getNom());
        joueur.tue(cible);
    }
}
