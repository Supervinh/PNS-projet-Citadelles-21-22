package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {
    @Test
    void notEmpty(){
        Joueur j1= new Joueur("1", true);
        assertEquals(MoteurDeJeu.or2Depart,j1.getOr());
        assertNotEquals(null,j1.getQuartiers());
    }

    @Test
    void aPiocheOr(){
        Joueur j2=new Joueur("2", true);
        j2.piocherOr();
        assertNotEquals(MoteurDeJeu.or2Depart,j2.getOr());
        assertEquals(MoteurDeJeu.orAPiocher+MoteurDeJeu.or2Depart, j2.getOr());
    }

    @Test
    void nomCorrect(){
        Joueur j3=new Joueur("3", true);
        assertEquals("3", j3.getNom());
        Joueur j4=new Joueur(null, true);
        assertEquals(null,j4.getNom());
    }
}