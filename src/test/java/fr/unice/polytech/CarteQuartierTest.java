package fr.unice.polytech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteQuartierTest {
    @Test
    void notEmpty(){
        CarteQuartier cq= new CarteQuartier(5.4,"Manoir","Noblesse",4);
        assertEquals(5.4, cq.getId());
        assertEquals("Manoir", cq.getName());
        assertEquals("Noblesse",cq.getJewl());
        assertEquals(4,cq.getPrice());
        assertEquals(null,cq.getDescription());

        CarteQuartier cq2= new CarteQuartier(5.4,"Manoir","Noblesse",4,"description");
        assertEquals(5.4, cq2.getId());
        assertEquals("Manoir", cq2.getName());
        assertEquals("Noblesse",cq2.getJewl());
        assertEquals(4,cq2.getPrice());
        assertNotEquals(null,cq2.getDescription());
    }

}