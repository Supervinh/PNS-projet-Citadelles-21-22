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
        m.initialiseJoueurs(joueurs, true);
        m.printJoueursInitialises(joueurs);
        m.piocherPersonnage(joueurs);
    }

    @Test
    void ordi1Gagne() {
        goodSetup();
        joueurs.get(0).ajouteOr(30);
        joueurs.get(0).construireQuartier();
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
        assertFalse(m.aFini(joueurs.get(0)));
    }

    @Test
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

    @Test
    void initialiserCartes4J() {
        MoteurDeJeu m = new MoteurDeJeu();
        ArrayList<Joueur> joueurs = new ArrayList<>();
        for (int i = 0; i < 4; i++) joueurs.add(new Joueur("CPU" + i));
        m.setJoueurs(joueurs);
        m.initialisePileCartes();
        assertEquals(5, MoteurDeJeu.deck.getPersonnages().size());
    }

    @Test
    void initialiserCartes5J() {
        MoteurDeJeu m = new MoteurDeJeu();
        ArrayList<Joueur> joueurs = new ArrayList<>();
        for (int i = 0; i < 5; i++) joueurs.add(new Joueur("CPU" + i));
        m.setJoueurs(joueurs);
        m.initialisePileCartes();
        assertEquals(6, MoteurDeJeu.deck.getPersonnages().size());
    }

    @Test
    void joueurPioche4Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        joueurs = new ArrayList<>();
        Joueur joueur = new Joueur();
        for (int i = 0; i < 4; i++) {
            joueurs.add(joueur);
            joueur = new Joueur();
        }
        m.setJoueurs(joueurs);
        m.joueurPiochePersonnage(joueurs, 0);
        assertNotNull(joueurs.get(0).getPersonnage());
    }

    @Test
    void joueurPioche5Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        joueurs = new ArrayList<>();
        Joueur joueur = new Joueur();
        for (int i = 0; i < 5; i++) {
            joueurs.add(joueur);
            joueur = new Joueur();
        }
        m.setJoueurs(joueurs);
        m.joueurPiochePersonnage(joueurs, 1);
        assertNotNull(joueurs.get(1).getPersonnage());
    }

    @Test
    void joueurPioche6Joueurs() {
        MoteurDeJeu m = new MoteurDeJeu();
        joueurs = new ArrayList<>();
        Joueur joueur = new Joueur();
        for (int i = 0; i < 6; i++) {
            joueurs.add(joueur);
            joueur = new Joueur();
        }
        m.setJoueurs(joueurs);
        m.joueurPiochePersonnage(joueurs, 2);
        assertNotNull(joueurs.get(2).getPersonnage());
    }

    @Test
    void remettreCarteCachee(){
        MoteurDeJeu m = new MoteurDeJeu();
        m.initialisePileCartes();
        assertNotNull(m.getCarteCachee());
        m.remettreCarteCachee();
        assertNull(m.getCarteCachee());
    }

}