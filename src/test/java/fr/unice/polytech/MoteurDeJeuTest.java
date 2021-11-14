package fr.unice.polytech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoteurDeJeuTest {

    String varToBeInitInSetup;

    @BeforeEach
    void setUp() {
        varToBeInitInSetup = "Citadelle Grp.H";
    }

    @Test
    void helloTest() {
        assertEquals(varToBeInitInSetup, MoteurDeJeu.hello());
    }
}