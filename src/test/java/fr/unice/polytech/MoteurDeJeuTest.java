package fr.unice.polytech;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        assertTrue((joueurs.get(0)).isRoi());
        for (int j = 1; j < joueurs.size(); j++) {
            assertFalse(joueurs.get(j).isRoi());
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

    @Test
    void initialiserCartes4J(){
        MoteurDeJeu m = new MoteurDeJeu();
        ArrayList<Joueur> joueurs = new ArrayList<>();
        for (int i = 0; i<4; i++){
            joueurs.add(new Joueur("CPU" + i));
        }
        m.setJoueurs(joueurs);
        m.initialisePileCartes();
        assertEquals(5, MoteurDeJeu.deck.getPersonnages().size());
    }

    @Test
    void initialiserCartes5J(){
        MoteurDeJeu m = new MoteurDeJeu();
        ArrayList<Joueur> joueurs = new ArrayList<>();
        for (int i = 0; i<5; i++){
            joueurs.add(new Joueur("CPU" + i));
        }
        m.setJoueurs(joueurs);
        m.initialisePileCartes();
        assertEquals(6, MoteurDeJeu.deck.getPersonnages().size());
    }


}