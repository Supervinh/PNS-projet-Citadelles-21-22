package fr.unice.polytech;

import fr.unice.polytech.Strategies.*;
import fr.unice.polytech.couleur.CouleurConsole;
import fr.unice.polytech.pouvoirs.IPouvoir;
import fr.unice.polytech.pouvoirs.PouvoirAssassin;

public class Strategie {
    private final Joueur joueur;
    private IStrategie iStrategie = new SuffisammentQuartier();
    private IPouvoir iPouvoir;

    public Strategie(Joueur joueur) {
        this.joueur = joueur;
    }

    private void actionPersonnage() {
        switch (this.joueur.getPersonnage().getNom()) {
            case "Assasin" -> this.iPouvoir = new PouvoirAssassin();
            case "Voleur" -> this.iPouvoir = null;
            case "Magicien" -> this.iPouvoir = null;
            case "Roi" -> this.iPouvoir = null; // Choisir une stratégie par personnage
            case "Évêque" -> this.iPouvoir = null;
            case "Marchand" -> this.iPouvoir = null;
            case "Architecte" -> this.iPouvoir = null;
            case "Condottiere" -> this.iPouvoir = null; // Choisir une stratégie par personnage
            default -> this.iPouvoir = null;
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
        if (this.joueur.getOr() < 4) {
            this.iStrategie = new EconomiserArgent();
            parDefaut = false;
        }
        if (this.joueur.getOr() > 10) {
            this.iStrategie = new SuffisammentOr();
            parDefaut = false;
        }
        if (parDefaut) {
            this.iStrategie = new SuffisammentQuartier();
        }
    }

    public void prochainTour() {
        this.choisirStrat();
        this.iStrategie.utiliserStrategie(this.joueur);
//        this.actionPersonnage();
//        this.iPouvoir.utiliserPouvoir();
    }

    @Override
    public String toString() {
        this.choisirStrat();
        return "Strategie=" + CouleurConsole.GREEN + this.iStrategie.nomStrategie() + CouleurConsole.RESET;
    }
}
