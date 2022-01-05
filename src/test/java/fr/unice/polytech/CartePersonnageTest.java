package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class CartePersonnageTest {

    final int ITERATIONS = 100;

    @RepeatedTest(ITERATIONS)
    void isNull() {
        CartePersonnage cp = new CartePersonnage(1, "Assassin", null);
        assertEquals("", cp.getDescription());
    }

    @RepeatedTest(ITERATIONS)
    void isNotEmpty() {
        CartePersonnage cp = new CartePersonnage(1, "Assassin", null, "description");
        assertEquals("Assassin", cp.getNom());
        assertEquals(1, cp.getId());
        assertNull(cp.getGemme());
        assertNotEquals(null, cp.getDescription());
    }
}