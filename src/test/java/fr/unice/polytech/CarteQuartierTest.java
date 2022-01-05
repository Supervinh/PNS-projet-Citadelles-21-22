package fr.unice.polytech;

import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CarteQuartierTest {

    final int ITERATIONS = 100;

    @RepeatedTest(ITERATIONS)
    void notEmpty() {
        CarteQuartier cq = new CarteQuartier(5.4, "Manoir", "Noblesse", 4);
        assertEquals(5.4, cq.getId());
        assertEquals("Manoir", cq.getNom());
        assertEquals("Noblesse", cq.getGemme());
        assertEquals(4, cq.getPrix());
        assertEquals("None", cq.getDescription());

        CarteQuartier cq2 = new CarteQuartier(5.4, "Manoir", "Noblesse", 4, "description");
        assertEquals(5.4, cq2.getId());
        assertEquals("Manoir", cq2.getNom());
        assertEquals("Noblesse", cq2.getGemme());
        assertEquals(4, cq2.getPrix());
        assertNotEquals(null, cq2.getDescription());
    }
}