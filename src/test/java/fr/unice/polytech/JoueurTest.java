package fr.unice.polytech;

import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {
    MoteurDeJeu moteurDeJeu;

    @BeforeEach
    public void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        MoteurDeJeu.setMessageLvl(Level.OFF);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void notEmpty() {
        Joueur j1 = new Joueur("1");
        assertEquals(MoteurDeJeu.or2Depart, j1.getOr());
        assertNotEquals(null, j1.getQuartiers());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void aPiocheOr() {
        Joueur j2 = new Joueur("2");
        j2.piocherOr();
        assertNotEquals(MoteurDeJeu.or2Depart, j2.getOr());
        assertEquals(MoteurDeJeu.orAPiocher + MoteurDeJeu.or2Depart, j2.getOr());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void nomCorrect() {
        Joueur j3 = new Joueur("3");
        assertEquals("3", j3.getNom());
        Joueur j4 = new Joueur(null);
        assertNull(j4.getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void piocherPersoDifferents() {
        Joueur j1 = new Joueur("1");
        Joueur j2 = new Joueur("2");
        j1.piocherPersonnage();
        j2.piocherPersonnage();
        assertNotEquals(j1.getPersonnage(), j2.getPersonnage());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void mainsDepartDifferentes() {
        Joueur j1 = new Joueur("1");
        Joueur j2 = new Joueur("2");
        assertNotEquals(j1.getQuartiers(), j2.getQuartiers());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void piocherCartesDifferentes() {
        Joueur j1 = new Joueur("1");
        Joueur j2 = new Joueur("2");
        CarteQuartier cq1 = j1.piocherQuartier();
        CarteQuartier cq2 = j2.piocherQuartier();
        assertNotEquals(cq1, cq2);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void pointsCorrect() {
        Joueur j1 = new Joueur("1");
        assertEquals(0, j1.getPoints());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void estRoi() {
        Joueur j1 = new Joueur("1");
        assertFalse(j1.isRoi());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void construire() {
        Joueur j1 = new Joueur("1");
        j1.ajouteOr(10);
        j1.construireQuartier();
        assertEquals(1, j1.getQuartiersConstruits().size());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void pasAssezArgent() {
        Joueur j1 = new Joueur("1");
        j1.ajouteOr(-1 * j1.getOr());
        j1.construireQuartier();
        assertEquals(0, j1.getQuartiersConstruits().size());
    }
}