package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

public class PouvoirArchitecte implements IPouvoir {
    @Override
    public void utiliserPouvoir(Joueur joueur, Joueur cible) {
        for (int i = 0; i < 2; i++) joueur.piocherQuartier();
        for (int i = 0; i < 2; i++) {
            switch (joueur.getNom2Strategie()) {
                case "Suffisamment de Quartiers", "Suffisamment d'Or" -> joueur.construireQuartier();
                default -> System.out.println("Aucun quartier supplémentaire construit");
            }
        }
    }


}