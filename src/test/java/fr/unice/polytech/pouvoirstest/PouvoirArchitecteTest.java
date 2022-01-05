package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.Strategie;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.pouvoirs.PouvoirArchitecte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PouvoirArchitecteTest {
    Joueur architecte;
    MoteurDeJeu moteurDeJeu;
    Strategie strategie;
    ArrayList<Joueur> joueurs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.setNbJoueurs(5);
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        architecte = joueurs.get(1);
        architecte.setPersonnage(new CartePersonnage(7, "Architecte", "None", "L'Architecte pioche deux cartes quartier en plus. il peut bâtir jusqu'à trois quartiers."));
        strategie = new Strategie(architecte);
        strategie.actionPersonnage();
        MoteurDeJeu.setMessageLvl(Level.OFF);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void piocheQuartierTest() {
        PouvoirArchitecte construire = Mockito.mock(PouvoirArchitecte.class);
        Mockito.doCallRealMethod().when(construire).utiliserPouvoir(architecte);
        System.out.println("Pioche \n");
        architecte.setQuartiers(new ArrayList<>());
        architecte.ajouteOr(-1 * (architecte.getOr()));
        construire.utiliserPouvoir(architecte);
        assertEquals(2, architecte.getQuartiers().size());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void construire3Quartiers() {
        PouvoirArchitecte construire = Mockito.mock(PouvoirArchitecte.class);
        Mockito.doCallRealMethod().when(construire).utiliserPouvoir(architecte);
        System.out.println("Construction \n");
        architecte.ajouteOr(20);
        construire.utiliserPouvoir(architecte);
        assertEquals(2, architecte.getQuartiersConstruits().size());
    }
}
