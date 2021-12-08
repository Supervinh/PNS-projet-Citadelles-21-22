package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.couleur.CouleurConsole;

public class PouvoirArchitecte implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        int n = 2;
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNomColoured()));
        boolean construire = false;

        for (int i = 0; i < n; i++) {
            System.out.print(CouleurConsole.printRed("| "));
            joueur.getQuartiers().add(joueur.piocherQuartier());
        }

        for (int i = 0; i < n; i++) {
            joueur.getStrategie().choisirType2Piochage();
            if (joueur.nombre2QuartiersConstructible() > 0 || joueur.getNomStrategie().equals("Suffisamment d'Or")) {
                joueur.construireQuartier();
                construire = true;
            }
        }

        if (!construire) {
            System.out.println(CouleurConsole.printRed("| ") + "Aucun quartier supplémentaire construit");
        }
    }


}