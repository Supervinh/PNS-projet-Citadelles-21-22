package fr.unice.polytech.cartes;

import fr.unice.polytech.MoteurDeJeu;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class CartePersonnageTest {
    @RepeatedTest(MoteurDeJeu.iterationTest)
    void isNull() {
        CartePersonnage cp = new CartePersonnage(1, "Assassin", null);
        assertEquals("", cp.getDescription());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void isNotEmpty() {
        CartePersonnage cp = new CartePersonnage(1, "Assassin", null, "description");
        assertEquals("Assassin", cp.getNom());
        assertEquals(1, cp.getId());
        assertNull(cp.getGemme());
        assertNotEquals(null, cp.getDescription());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void toStringVide() {
        CartePersonnage cartePersonnageVide = new CartePersonnage(0.0, "", "");
        System.out.println(cartePersonnageVide);
        assertEquals("CartePersonnage{id=" + cartePersonnageVide.getIdColoured() + ", nom=" + cartePersonnageVide.getNomColoured() + ", gemme=" + cartePersonnageVide.getGemmeColoured() + ", description=" + cartePersonnageVide.getDescriptionColoured() + "}", cartePersonnageVide.toString());
    }
}