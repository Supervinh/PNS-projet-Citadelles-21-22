package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public interface IPouvoir {
    void utiliserPouvoir(Joueur joueur);

    default void recupererTaxes(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| ") + "Recuperation des " + CouleurConsole.printGold("Taxes") + " des Gemmes " + joueur.getPersonnage().getGemmeColoured());
        int count = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme())).count();
        if (joueur.contientQuartier("École de magie")) {
            count += 1;
        }
        joueur.ajouteOr(count);
        boolean plurielle = count > 1;
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a pioché " + CouleurConsole.printGold("" + count) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("Or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}
