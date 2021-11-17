package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartePersonnageTest {
    @Test
    void isNull(){
        CartePersonnage cp= new CartePersonnage(1,"Assassin",null);
        assertEquals(null,cp.getDescription());
    }

    @Test
    void isNotEmpty(){
        CartePersonnage cp= new CartePersonnage(1,"Assassin",null,"description");
        assertEquals("Assassin",cp.getNom());
        assertEquals(1,cp.getId());
        assertEquals(null,cp.getGemme());
        assertNotEquals(null,cp.getDescription());
    }
}