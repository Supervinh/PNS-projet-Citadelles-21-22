package fr.unice.polytech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoteurDeJeuTest {
    ArrayList<Joueur> joueurs;
    MoteurDeJeu m;
    @BeforeEach
    void goodsetup(){m=new MoteurDeJeu();
        joueurs=new ArrayList<>();
        m.initialiseJoueur(joueurs);
        m.piocherPersonnage(joueurs);}

    @Test
    void ordi1gagne(){

    }

}