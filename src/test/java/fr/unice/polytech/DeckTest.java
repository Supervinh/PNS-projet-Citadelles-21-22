package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    final int ITERATIONS = 100;
    Deck d;

    @BeforeEach
    void setUp() {
        d = new Deck();
    }

    @RepeatedTest(ITERATIONS)
    void piocheQuartier() {
        assertNotNull(d.piocherQuartier());
    }

    @RepeatedTest(ITERATIONS)
    void piochePersoDifferents() {
        CartePersonnage cp1 = d.piocherPersonnage();
        CartePersonnage cp2 = d.piocherPersonnage();
        assertNotEquals(cp1, cp2);
    }

    @RepeatedTest(ITERATIONS)
    void bienajoute() {
        int i = d.getPersonnages().size();
        d.ajoutePersonnage(d.piocherPersonnage());
        assertEquals(i, d.getPersonnages().size());
        d.piocherPersonnage();
        assertNotEquals(i, d.getPersonnages().size());
    }
}