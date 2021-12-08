package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PouvoirEvequeTest {
    Joueur joueur;
    MoteurDeJeu moteurDeJeu;
    Strategie strategie;
    ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        joueur = joueurs.get(1);
        joueur.setPersonnage(new CartePersonnage(5, "Évêque", "Religion", "L'Évêque ne peut pas être attaqué par le Condottière. Chaque quartier religieux qu'il possède lui rapporte une pièce d'or."));
        quartier = new CarteQuartier(3, "Monastère", "Religion", 3);
        quartiers.add(quartier);
        strategie = new Strategie(joueur);
        strategie.actionPersonnage();
    }

    @Test
    void taxesAjoutTest() {
        joueur.setQuartiers(quartiers);
        joueur.ajouteOr(1);
        joueur.construireQuartier();
        strategie.prochainTour();
        assertEquals(3, joueur.getOr());

    }

}
