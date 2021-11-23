package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.concurrent.atomic.AtomicInteger;

public class PouvoirRoi implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        MoteurDeJeu.joueurs.forEach(j -> j.setEstRoi(false));
        joueur.setEstRoi(true);
        System.out.println(joueur.getNom() + " est le Nouveau " + CouleurConsole.printGold("Roi"));
        this.recupererTaxes(joueur);
    }

    public void recupererTaxes(Joueur joueur) {
        System.out.println("Collecting Taxes from " + joueur.getPersonnage().getGemme());
        AtomicInteger cpt = new AtomicInteger(1);
        joueur.getQuartiersConstruits().stream()
                .filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme()))
                .forEach(quartier -> {
                    joueur.ajouteOr(1);
                    cpt.getAndIncrement();
                });
        boolean plurielle = cpt.get() > 1;
        System.out.println(joueur.getNom() + " a pioché " + CouleurConsole.printGold("" + cpt) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}
