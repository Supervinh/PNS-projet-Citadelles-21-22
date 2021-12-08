package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirRoi implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        MoteurDeJeu.joueurs.forEach(j -> j.setRoi(false));
        joueur.setRoi(true);
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " est le Nouveau " + CouleurConsole.printGold("Roi"));
        System.out.println(CouleurConsole.printRed("|"));
        this.recupererTaxes(joueur);
    }
}
