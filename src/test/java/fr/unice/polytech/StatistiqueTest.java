package fr.unice.polytech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatistiqueTest {

    Statistique statistique;
    String[][] data;

    @BeforeEach
    void setUp() {
        this.statistique = new Statistique("Test");
        this.data = new String[][]{new String[]{"nom", "victoire", "parties", "ratio"}, new String[]{"CPU1", "1", "100", "0.1"}, new String[]{"CPU2", "2", "200", "0.2"}, new String[]{"CPU3", "3", "300", "0.3"}, new String[]{"CPU4", "4", "400", "0.4"}};
    }

    @Test
    void printData() {
        assertEquals("[[nom, victoire, parties, ratio], [CPU1, 1, 100, 0.1], [CPU2, 2, 200, 0.2], [CPU3, 3, 300, 0.3], [CPU4, 4, 400, 0.4]]", Arrays.deepToString(this.data));
    }

    @Test
    void ecrireDataPasDeThrow() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        MoteurDeJeu moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.jouer();
        this.statistique.ajoutStats(moteurDeJeu);
        this.statistique.ajoutAuxCSV();
    }

    @Test
    void lireDataPasDeThrow() {
        this.statistique.printStatTableau();
    }
}