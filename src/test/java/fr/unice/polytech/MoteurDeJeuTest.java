package fr.unice.polytech;

import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class MoteurDeJeuTest {
    ArrayList<Joueur> joueurs;
    MoteurDeJeu m;

    void goodSetup() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m = new MoteurDeJeu();
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.printJoueursInitialises(joueurs);
        m.piocherPersonnage(joueurs);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void moteurDeJeuIdentiqueAuDemarrage() {
        assertEquals(new MoteurDeJeu(), new MoteurDeJeu());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void ordi1Gagne() {
        goodSetup();
        joueurs.get(0).ajouteOr(30);
        joueurs.get(0).construireQuartier();
        assertEquals(joueurs.get(0), m.obtenirGagnant(joueurs));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void trouverRoi() {
        goodSetup();
        m.trouverQuiEstRoi(joueurs);
        assertTrue((joueurs.get(0)).isRoi());
        for (int j = 1; j < joueurs.size(); j++) {
            assertFalse(joueurs.get(j).isRoi());
        }
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void jeuPasFini() {
        goodSetup();
        assertFalse(m.aFini(joueurs.get(0)));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void jeuFini() {
        goodSetup();
        joueurs.get(0).ajouteOr(30);
        for (int i = 0; i < 20; i++) joueurs.get(0).ajouterQuartierEnMain();
        for (int i = 0; i < 8; i++) {
            joueurs.get(0).construireQuartier();
            joueurs.get(0).ajouteOr(8);
        }
        assertEquals(8, joueurs.get(0).getQuartiersConstruits().size());
        assertTrue(m.aFini(joueurs.get(0)));
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void initialiserCartes4J() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.setNbJoueurs(4);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.initialisePileCartes();
        assertEquals(5, MoteurDeJeu.deck.getPersonnages().size());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void initialiserCartes5J() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.initialisePileCartes();
        assertEquals(6, MoteurDeJeu.deck.getPersonnages().size());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void joueurPioche4Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        Random indice = new Random();
        int indicePersonnage = indice.nextInt(4);
        m.joueurPiochePersonnage(joueurs, indicePersonnage);
        assertNotNull(joueurs.get(indicePersonnage).getPersonnage());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void joueurPioche5Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        Random indice = new Random();
        int indicePersonnage = indice.nextInt(5);
        m.joueurPiochePersonnage(joueurs, indicePersonnage);
        assertNotNull(joueurs.get(indicePersonnage).getPersonnage());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void joueurPioche6Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.setNbJoueurs(6);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        Random indice = new Random();
        int indicePersonnage = indice.nextInt(6);
        m.joueurPiochePersonnage(joueurs, indicePersonnage);
        assertNotNull(joueurs.get(indicePersonnage).getPersonnage());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void joueurPioche7Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.setNbJoueurs(7);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.initialisePileCartes();
        for (int i = 0; i < 6; i++) MoteurDeJeu.deck.piocherPersonnage();
        Random indice = new Random();
        int indicePersonnage = indice.nextInt(7);
        m.joueurPiochePersonnage(joueurs, indicePersonnage);
        assertNotNull(joueurs.get(indicePersonnage).getPersonnage());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void remettreCarteCachee() {
        MoteurDeJeu m = new MoteurDeJeu();
        m.initialisePileCartes();
        assertNotNull(m.getCarteCachee());
        m.remettreCarteCachee();
        assertNull(m.getCarteCachee());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void tour2JeuException() {
        goodSetup();
        assertThrows(java.lang.IllegalArgumentException.class, () -> m.tourDeJeu(joueurs.get(0)));
    }
}