package fr.unice.polytech.strategiestest;

import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.QuartierMerveilles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuartierMerveillesTest {
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
        quartier = new CarteQuartier(18, "Cour des miracles", "Prestige", 5);
        quartiers.add(quartier);
    }

    @Test
    void cartePrestige() {
        QuartierMerveilles pouvoir = Mockito.mock(QuartierMerveilles.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier quartierPrestige = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartier, quartierPrestige);
    }
}
