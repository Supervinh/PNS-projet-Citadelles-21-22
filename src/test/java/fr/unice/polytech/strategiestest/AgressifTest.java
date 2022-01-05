package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgressifTest {
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur agressif;

    @BeforeEach
    void setUp() {
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        agressif = joueurs.get(2);
        MoteurDeJeu.setMessageLvl(Level.OFF);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixAssassin() {
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixCondottiere() {
        Joueur sixQuartiersConstruits = joueurs.get(0);
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.setQuartiersConstruits(sixQuartiersConstruits.getQuartiers());
        assertEquals(6, sixQuartiersConstruits.getQuartiersConstruits().size());
        agressif.piocherPersonnage();
        assertEquals("Condottiere", agressif.getPersonnage().getNom());
    }
}
