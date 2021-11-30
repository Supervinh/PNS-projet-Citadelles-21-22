package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoteurDeJeuTest {
    ArrayList<Joueur> joueurs;
    MoteurDeJeu m;

    void goodsetup(){m=new MoteurDeJeu();
        joueurs=new ArrayList<>();
        m.initialiseJoueurs(joueurs);
        m.printJoueursInitialises(joueurs);
        m.piocherPersonnage(joueurs);}

    @Test
    void ordi1gagne(){
        goodsetup();
        for(int i=0;i<5;i++){
            joueurs.get(i).setPoints(30-i);
        }
        assertEquals(1,m.obtenirGagnant(joueurs));
    }

    @Test
    void trouverRoi(){
        goodsetup();
        m.trouverQuiEstRoi(joueurs);
        assertTrue((joueurs.get(0)).isEstRoi());
        for (int j=1; j < joueurs.size() ;j++){
            assertFalse(joueurs.get(j).isEstRoi());
        }
    }

    @Test
    void jeuPasFini(){
        goodsetup();
        assertFalse(m.verifieFini(joueurs.get(0)));
    }

    @Test
    void jeuFini(){
        goodsetup();
        joueurs.get(0).setOr(10000);
        for (int i=0; i < 8; i++) {
            joueurs.get(0).piocherQuartier();
            joueurs.get(0).construireQuartier();
        }
        assertEquals(8, joueurs.get(0).getQuartiersConstruits().size());
        assertTrue(m.verifieFini(joueurs.get(0)));
    }


}