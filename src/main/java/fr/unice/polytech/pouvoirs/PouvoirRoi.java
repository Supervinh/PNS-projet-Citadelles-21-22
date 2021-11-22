package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirRoi implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur, Joueur cible) {
        MoteurDeJeu.joueurs.forEach(j -> j.setEstRoi(false));
        joueur.setEstRoi(true);
        System.out.println(joueur.getNom() + " est le Nouveau " + CouleurConsole.printGold("Roi"));
    }
}
