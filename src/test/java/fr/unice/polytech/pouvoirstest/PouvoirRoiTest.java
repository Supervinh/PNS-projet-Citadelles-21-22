package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PouvoirRoiTest {
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
        joueur.setPersonnage(new CartePersonnage(4, "Roi", "Noblesse", "Le roi prend la carte Couronne et choisira en premier son personnage au prochain tour. Chaque quartier noble qu'il possède lui rapporte une pièce d'or."));
        quartier = new CarteQuartier(5, "Manoir", "Noblesse", 3);
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
        assertTrue(joueur.isRoi());
        assertEquals(3, joueur.getOr());

    }

}
