package fr.unice.polytech.cartes;

import fr.unice.polytech.MoteurDeJeu;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CarteQuartierTest {

    @RepeatedTest(MoteurDeJeu.iterationTest)
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

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void toStringVide() {
        CarteQuartier carteQuartierVide = new CarteQuartier(0.0, "", "", 0.0);
        assertEquals("CarteQuartier{id=" + carteQuartierVide.getIdColoured() + ", nom=" + carteQuartierVide.getNomColoured() + ", gemme=" + carteQuartierVide.getGemmeColoured() + ", prix=" + carteQuartierVide.getPrixColoured() + ", description=" + carteQuartierVide.getDescriptionColoured() + "}", carteQuartierVide.toString());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void changerLaGemme() {
        CarteQuartier carteQuartierVide = new CarteQuartier(0.0, "", "", 0.0);
        CarteQuartier carteQuartierNouvelleGemme = new CarteQuartier(carteQuartierVide.getId(), carteQuartierVide.getNom(), carteQuartierVide.getGemme(), carteQuartierVide.getPrix());
        carteQuartierNouvelleGemme.setGemme("Nouvelle Gemme");
        assertNotEquals(carteQuartierVide.getGemme(), carteQuartierNouvelleGemme.getGemme());
    }
}