package fr.unice.polytech;

import fr.unice.polytech.piocher.*;
import fr.unice.polytech.pouvoirs.*;

public class Strategie {
    private final Joueur joueur;
    private IPiocher iPiocher = new SuffisammentQuartier();
    private IPouvoir iPouvoir;

    public Strategie(Joueur joueur) {
        this.joueur = joueur;
    }

    public void actionPersonnage() {
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

    public void choisirStrat() {
        boolean parDefaut = true;
        if (this.joueur.getQuartiers().size() < 2) { // Check le nombre de bonnes cartes dans la main
            this.iPiocher = new RechercheMeilleurQuartier();
            parDefaut = false;
        }
        if (this.joueur.getQuartiers().size() > 6) {
            this.iPiocher = new SuffisammentQuartier();
            parDefaut = false;
        }
        if (this.joueur.getOr() < 2) {
            this.iPiocher = new EconomiserArgent();
            parDefaut = false;
        }
        if (this.joueur.getOr() > 6) {
            this.iPiocher = new SuffisammentOr();
            parDefaut = false;
        }
        if (parDefaut) {
            this.iPiocher = new SuffisammentQuartier();
        }
    }

    public void prochainTour() {
        this.choisirStrat();
        String nomPersonnage = joueur.getPersonnage().getNom();
        if (nomPersonnage.equals("Marchand") || nomPersonnage.equals("Évêque")) {
            this.actionPersonnage();
            this.iPouvoir.utiliserPouvoir(this.joueur);
            System.out.println();
            this.choisirStrat();
            if (this.iPiocher != null) this.iPiocher.utiliserStrategie(this.joueur);
        } else {
            if (this.iPiocher != null) this.iPiocher.utiliserStrategie(this.joueur);
            this.choisirStrat();
            System.out.println();
            this.actionPersonnage();
            this.iPouvoir.utiliserPouvoir(this.joueur);
        }
    }

    public IPiocher getiPiocher() {
        this.choisirStrat();
        return iPiocher;
    }

    @Override
    public String toString() {
        return this.iPiocher.nomStrategie();
    }
}
