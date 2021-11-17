package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {
    @Test
    void notEmpty(){
        Joueur j1= new Joueur("1");
        assertEquals(MoteurDeJeu.or2Depart,j1.getOr());
        assertNotEquals(null,j1.getQuartiers());
    }

    @Test
    void aPiocheOr(){
        Joueur j2=new Joueur("2");
        j2.piocherOr();
        assertNotEquals(MoteurDeJeu.or2Depart,j2.getOr());
        assertEquals(MoteurDeJeu.orAPiocher+MoteurDeJeu.or2Depart, j2.getOr());
    }

    @Test
    void nomCorrect(){
        Joueur j3=new Joueur("3");
        assertEquals("3", j3.getNom());
        Joueur j4=new Joueur(null);
        assertEquals(null,j4.getNom());
    }

    @Test
    void piocherPersoDifferents(){
        Joueur j1=new Joueur("1");
        Joueur j2=new Joueur("2");
        j1.piocherPersonnage();
        j2.piocherPersonnage();
        assertNotEquals(j1.getPersonnage(),j2.getPersonnage());
    }

    @Test
    void piocherCartesDifferentes(){
        Joueur j1=new Joueur("1");
        Joueur j2=new Joueur("2");
        j1.piocherQuartier();
        j2.piocherQuartier();
        assertNotEquals(j1.getQuartiers(),j2.getQuartiers());
    }

    @Test
    void pointsCorrect(){
        Joueur j1=new Joueur("1");
        assertEquals(0,j1.getPoints());
    }

    @Test
    void estRoi(){
        Joueur j1=new Joueur("1");
        assertFalse(j1.isEstRoi());
    }
}