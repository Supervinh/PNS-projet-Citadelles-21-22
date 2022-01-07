package fr.unice.polytech.cartes;

import fr.unice.polytech.MoteurDeJeu;
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

    @Test
    void toStringVide() {
        CartePersonnage cartePersonnageVide = new CartePersonnage(0.0, "", "");
        assertEquals("CartePersonnage{id=" + cartePersonnageVide.getIdColoured() + ", nom=" + cartePersonnageVide.getNomColoured() + ", gemme=" + cartePersonnageVide.getGemmeColoured() + ", description=" + cartePersonnageVide.getDescriptionColoured() + "}", cartePersonnageVide.toString());
    }
}