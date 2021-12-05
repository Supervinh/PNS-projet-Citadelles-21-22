package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoteurDeJeuTest {
    ArrayList<Joueur> joueurs;
    MoteurDeJeu m;

    void goodSetup() {
        m = new MoteurDeJeu();
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs);
        m.printJoueursInitialises(joueurs);
        m.piocherPersonnage(joueurs);
    }

    @Test
    void ordi1Gagne() {
        goodSetup();
        for (int i = 0; i < 5; i++) {
            joueurs.get(i).setPoints(30 - i);
        }
        assertEquals(joueurs.get(0), m.obtenirGagnant(joueurs));
    }

    @Test
    void trouverRoi() {
        goodSetup();
        m.trouverQuiEstRoi(joueurs);
        assertTrue((joueurs.get(0)).isEstRoi());
        for (int j = 1; j < joueurs.size(); j++) {
            assertFalse(joueurs.get(j).isEstRoi());
        }
    }

    @Test
    void jeuPasFini() {
        goodSetup();
        assertFalse(m.verifieFini(joueurs.get(0)));
    }

    @Test
    void jeuFini() {
        goodSetup();
        joueurs.get(0).setOr(10000);
        for (int i = 0; i < 20; i++) joueurs.get(0).ajouterQuartierEnMain();
        for (int i = 0; i < 8; i++) joueurs.get(0).construireQuartier();
        assertEquals(8, joueurs.get(0).getQuartiersConstruits().size());
        assertTrue(m.verifieFini(joueurs.get(0)));
    }


}