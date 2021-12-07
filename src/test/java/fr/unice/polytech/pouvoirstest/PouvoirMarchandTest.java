package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PouvoirMarchandTest {
    Joueur joueur;
    MoteurDeJeu moteurDeJeu;
    Strategie strategie;
    ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs);
        joueur = joueurs.get(1);
        joueur.setPersonnage(new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or."));
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
        strategie = new Strategie(joueur);
    }

    @Test
    void taxesAjoutTest() {
        joueur.setQuartiers(quartiers);
        joueur.ajouteOr(1);
        joueur.construireQuartier();
        strategie.prochainTour();
        assertEquals(2, joueur.getOr());

    }

}
