package fr.unice.polytech;

import fr.unice.polytech.Strategies.*;
import fr.unice.polytech.couleur.CouleurConsole;
import fr.unice.polytech.pouvoirs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            case "Voleur" -> this.iPouvoir = new PouvoirVoleur();
            case "Magicien" -> this.iPouvoir = new PouvoirMagicien();
            case "Roi" -> this.iPouvoir = new PouvoirRoi();
            case "Évêque" -> this.iPouvoir = new PouvoirEveque();
            case "Marchand" -> this.iPouvoir = new PouvoirMarchand();
            case "Architecte" -> this.iPouvoir = new PouvoirArchitecte();
            case "Condottiere" -> this.iPouvoir = new PouvoirCondottiere();
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
    }

    @Override
    public String toString() {
        return this.iStrategie.nomStrategie();
    }
}
