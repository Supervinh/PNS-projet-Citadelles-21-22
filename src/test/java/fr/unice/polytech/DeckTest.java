package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    void piocheQuartier(){
        Deck d=new Deck();
        assertNotNull(d.piocherQuartier());
    }
}