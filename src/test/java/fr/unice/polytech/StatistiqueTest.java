package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvEcriture;
import fr.unice.polytech.lecteurFichiers.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.logging.Level;

class StatistiqueTest {
    Statistique statistique;
    String[][] data = new CsvReader().getData();

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        this.statistique = new Statistique();
    }

    // Lancer toute la method sans modifier le ficher CSV.
    @RepeatedTest(MoteurDeJeu.iterationTest)
    void pasErreurEnAjoutantauCSV() {
        MoteurDeJeu mj = new MoteurDeJeu();
        CsvReader csvReader = Mockito.mock(CsvReader.class);
        CsvEcriture ecritureCsv = Mockito.mock(CsvEcriture.class);
        Statistique statistiqueMock = Mockito.mock(Statistique.class);
        Mockito.doCallRealMethod().when(statistiqueMock).ajoutStats(mj);
        Mockito.when(csvReader.getData()).thenReturn(new String[0][0]);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void pasErreurEnAffichant() {
        this.statistique.printStatTableau();
    }
}