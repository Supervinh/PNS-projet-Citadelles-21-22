package fr.unice.polytech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck d;

    @BeforeEach
    void setUp() {
        d = new Deck();
    }

    @Test
    void piocheQuartier() {
        assertNotNull(d.piocherQuartier());
    }

    @Test
    void piochePersoDifferents() {
        CartePersonnage cp1 = d.piocherPersonnage();
        CartePersonnage cp2 = d.piocherPersonnage();
        assertNotEquals(cp1, cp2);
    }

    @Test
    void bienajoute() {
        int i = d.getPersonnages().size();
        d.ajoutePersonnage(d.piocherPersonnage());
        assertEquals(i, d.getPersonnages().size());
        d.piocherPersonnage();
        assertNotEquals(i, d.getPersonnages().size());
    }
}