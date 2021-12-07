package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirArchitecte implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int n = 2;
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        for (int i = 0; i < n; i++) {
            System.out.print(CouleurConsole.printRed("| "));
            joueur.getQuartiers().add(joueur.piocherQuartier());
        }
        for (int i = 0; i < n; i++) {
            joueur.getStrategie().choisirType2Piochage();
            switch (joueur.getNomStrategie()) {
                case "Suffisamment de Quartiers", "Suffisamment d'Or" -> joueur.construireQuartier();
                default -> System.out.println(CouleurConsole.printRed("| ") + "Aucun quartier supplémentaire construit");
            }
        }
    }


}