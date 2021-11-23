package fr.unice.polytech;

import fr.unice.polytech.Strategies.*;
import fr.unice.polytech.couleur.CouleurConsole;
import fr.unice.polytech.pouvoirs.*;

public class Strategie {
    private final Joueur joueur;
    private IStrategie iStrategie = new SuffisammentQuartier();
    private IPouvoir iPouvoir;

    public Strategie(Joueur joueur) {
        this.joueur = joueur;
    }

    private void actionPersonnage() {
        switch (this.joueur.getPersonnage().getNom()) {
            case "Assassin" -> iPouvoir = new PouvoirAssassin();
            case "Voleur" -> iPouvoir = new PouvoirVoleur();
            case "Magicien" -> iPouvoir = new PouvoirMagicien();
            case "Roi" -> iPouvoir = new PouvoirRoi();
            case "Évêque" -> iPouvoir = new PouvoirEveque();
            case "Marchand" -> iPouvoir = new PouvoirMarchand();
            case "Architecte" -> iPouvoir = new PouvoirArchitecte();
            case "Condottiere" -> iPouvoir = new PouvoirCondottiere();
            default -> iPouvoir = null;
        }
    }

    private void choisirStrat() {
        boolean parDefaut = true;
        if (this.joueur.getQuartiers().size() < 2) { // Check le nombre de bonnes cartes dans la main
            this.iStrategie = new RechercheMeilleurQuartier();
            parDefaut = false;
        }
        if (this.joueur.getQuartiers().size() > 6) {
            this.iStrategie = new SuffisammentQuartier();
            parDefaut = false;
        }
        if (this.joueur.getOr() < 2) {
            this.iStrategie = new EconomiserArgent();
            parDefaut = false;
        }
        if (this.joueur.getOr() > 6) {
            this.iStrategie = new SuffisammentOr();
            parDefaut = false;
        }
        if (parDefaut) {
            this.iStrategie = new SuffisammentQuartier();
        }
    }

    public void prochainTour() {
        this.choisirStrat();
        System.out.println("Strategie=" + CouleurConsole.printGreen(this.joueur.getNom2Strategie()));
        if (this.iStrategie != null) this.iStrategie.utiliserStrategie(this.joueur);
        this.actionPersonnage();
        this.iPouvoir.utiliserPouvoir(this.joueur);
    }

    @Override
    public String toString() {
        return this.iStrategie.nomStrategie();
    }
}
