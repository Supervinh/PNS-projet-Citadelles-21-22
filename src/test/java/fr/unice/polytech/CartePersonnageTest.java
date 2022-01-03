package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartePersonnageTest {
    @Test
    void isNull() {
        CartePersonnage cp = new CartePersonnage(1, "Assassin", null);
        assertEquals("", cp.getDescription());
    }

    @Test
    void isNotEmpty() {
        CartePersonnage cp = new CartePersonnage(1, "Assassin", null, "description");
        assertEquals("Assassin", cp.getNom());
        assertEquals(1, cp.getId());
        assertNull(cp.getGemme());
        assertNotEquals(null, cp.getDescription());
    }
}