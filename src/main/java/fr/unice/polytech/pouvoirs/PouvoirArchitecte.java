package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

public class PouvoirArchitecte implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int n = 2;
        for (int i = 0; i < n; i++) joueur.piocherQuartier();
        for (int i = 0; i < n; i++) {
            switch (joueur.getNom2Strategie()) {
                case "Suffisamment de Quartiers", "Suffisamment d'Or" -> joueur.construireQuartier();
                default -> System.out.println("Aucun quartier supplémentaire construit");
            }
        }
    }


}