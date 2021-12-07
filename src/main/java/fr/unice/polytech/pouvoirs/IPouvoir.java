package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public interface IPouvoir {
    void utiliserPouvoir(Joueur joueur);

    default void recupererTaxes(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| ") + "\n" + CouleurConsole.printRed("| ") + "Recuperation des " + CouleurConsole.printGold("Taxes") + " des Gemmes " + CouleurConsole.printPurple(joueur.getPersonnage().getGemme()));
        int count = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme())).count();
        for(int i=0;i<joueur.getQuartiersConstruits().size();i++){
            if(joueur.getQuartiersConstruits().get(i).getNom().equals("École de magie")) count+=1;
        }
        joueur.ajouteOr(count);
        boolean plurielle = count > 1;
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a pioché " + CouleurConsole.printGold("" + count) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}
