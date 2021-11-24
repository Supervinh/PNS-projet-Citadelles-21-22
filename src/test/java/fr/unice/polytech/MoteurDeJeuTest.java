package fr.unice.polytech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoteurDeJeuTest {
    ArrayList<Joueur> joueurs;
    MoteurDeJeu m;

    void goodsetup(){m=new MoteurDeJeu();
        joueurs=new ArrayList<>();
        m.initialiseJoueur(joueurs);
        m.piocherPersonnage(joueurs);}

    @Test
    void ordi1gagne(){
        goodsetup();
        assertTrue(m.joueursDifferents(joueurs));
        for(int i=0;i<5;i++){
            joueurs.get(i).setPoints(30-i);
        }
        assertEquals(1,m.obtenirGagnant(joueurs).size());
        assertEquals(m.obtenirGagnant(joueurs).get(0).getId(),1);
    }


}