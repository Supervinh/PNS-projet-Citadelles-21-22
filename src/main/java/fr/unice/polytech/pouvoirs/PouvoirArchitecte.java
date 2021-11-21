package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;

public class PouvoirArchitecte implements IPouvoir {
    @Override
    public void utiliserPouvoir(Joueur joueur, Joueur cible) {
        String strategie = joueur.getNom2Strategie();
        for (int i = 0; i < 2; i++) joueur.piocherQuartier();
        ArrayList<CarteQuartier> constructions = joueur.getQuartiersConstruits();
        for (int i = 0; i < 2; i++) {
            switch (strategie) {
                //case "Strategie=Suffisamment de Quartiers":
                //case "Strategie=Suffisamment d'Or":
                case "Strategie=" + CouleurConsole.GREEN + "Suffisamment de Quartiers" + CouleurConsole.RESET:
                case "Strategie=" + CouleurConsole.GREEN + "Suffisamment d'Or" + CouleurConsole.RESET:
                    joueur.construireQuartier();
                    break;
                default:
                    System.out.println("Aucun quartier supplémentaire construit");
                    break;
            }
        }
    }


}