package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck d;

    @BeforeEach
    void setUp() {
        d = new Deck();
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void piocheQuartier() {
        assertNotNull(d.piocherQuartier());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void piochePersoDifferents() {
        CartePersonnage cp1 = d.piocherPersonnage();
        CartePersonnage cp2 = d.piocherPersonnage();
        assertNotEquals(cp1, cp2);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void bienajoute() {
        int i = d.getPersonnages().size();
        d.ajoutePersonnage(d.piocherPersonnage());
        assertEquals(i, d.getPersonnages().size());
        d.piocherPersonnage();
        assertNotEquals(i, d.getPersonnages().size());
    }
}