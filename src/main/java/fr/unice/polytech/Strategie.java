package fr.unice.polytech;

import fr.unice.polytech.Strategies.*;
import fr.unice.polytech.couleur.CouleurConsole;
import fr.unice.polytech.pouvoirs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Strategie {
    private final Joueur joueur;
    private Joueur cible;
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
            case "Roi" -> this.iPouvoir = new PouvoirRoi(); // Choisir une stratégie par personnage
            case "Évêque" -> this.iPouvoir = new PouvoirEveque();
            case "Marchand" -> this.iPouvoir = new PouvoirMarchand();
            case "Architecte" -> this.iPouvoir = null;
            case "Condottiere" -> this.iPouvoir = new PouvoirCondottiere(); // Choisir une stratégie par personnage
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
        System.out.println(this.joueur.getNom2Strategie());
        if (this.iStrategie != null) this.iStrategie.utiliserStrategie(this.joueur);

        this.actionPersonnage();
        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.joueurs));
        cibles.remove(this.joueur);
        this.cible = cibles.get(new Random().nextInt(cibles.size()));
        if (this.iPouvoir != null) this.iPouvoir.utiliserPouvoir(this.joueur, this.cible);
    }

    @Override
    public String toString() {
        return "Strategie=" + CouleurConsole.GREEN + this.iStrategie.nomStrategie() + CouleurConsole.RESET;
    }
}
