package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.Strategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PouvoirArchitecteTest {
    Joueur joueur;
    MoteurDeJeu moteurDeJeu;
    Strategie strategie;
    ArrayList<Joueur> joueurs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        joueur = joueurs.get(1);
        joueur.setPersonnage(new CartePersonnage(7, "Architecte", "None", "L'Architecte pioche deux cartes quartier en plus. il peut bâtir jusqu'à trois quartiers."));
        strategie = new Strategie(joueur);
        strategie.actionPersonnage();
    }

    @Test
    void piocheQuartierTest() {
        System.out.println("Pioche \n");
        joueur.setQuartiers(new ArrayList<>());
        joueur.setOr(-5);
        strategie.prochainTour();
        assertEquals(joueur.getQuartiers().size(), 2);
    }

    @Test
    void construire3Quartiers() {
        System.out.println("Construction \n");
        joueur.setOr(100);
        strategie.prochainTour();
        assertEquals(joueur.getQuartiersConstruits().size(), 3);
    }

}
