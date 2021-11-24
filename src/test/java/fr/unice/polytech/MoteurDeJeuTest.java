package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoteurDeJeuTest {
    ArrayList<Joueur> joueurs;
    MoteurDeJeu m;

    void goodsetup(){m=new MoteurDeJeu();
        joueurs=new ArrayList<>();
        m.printJoueursInitialises(joueurs);
        m.piocherPersonnage(joueurs);}

    @Test
    void ordi1gagne(){
        goodsetup();
        for(int i=0;i<5;i++){
            joueurs.get(i).setPoints(30-i);
        }
        assertEquals(1,m.obtenirGagnant(joueurs).size());
        assertEquals(m.obtenirGagnant(joueurs).get(0).getId(),1);
    }




}