package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirRoi implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        MoteurDeJeu.joueurs.forEach(j -> j.setRoi(false));
        joueur.setRoi(true);
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " est le Nouveau " + CouleurConsole.printGold("Roi"));
        this.recupererTaxes(joueur);
    }
}
