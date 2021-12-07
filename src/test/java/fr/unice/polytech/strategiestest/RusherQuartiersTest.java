package fr.unice.polytech.strategiestest;

import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.strategie.RusherQuartiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RusherQuartiersTest {
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
        quartier = new CarteQuartier(18, "Cour des miracles", "Prestige", 5);
        quartiers.add(quartier);
    }


    @Test
    void carteRush() {
        RusherQuartiers pouvoir = Mockito.mock(RusherQuartiers.class);
        Mockito.doCallRealMethod().when(pouvoir).quartierAConstruire(quartiers);
        CarteQuartier carteRusher = pouvoir.quartierAConstruire(quartiers);
        System.out.println(carteRusher);
        assertEquals(quartiers.get(0), carteRusher);
    }
}
