package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class StatistiqueTest {
    Statistique statistique;
    String[][] data = new CsvReader().getData();

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        this.statistique = new Statistique();
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void ajoutStats() {
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void ajoutAuxCSV() {
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void printStatTableauDeuxIdentique() {
    }
}