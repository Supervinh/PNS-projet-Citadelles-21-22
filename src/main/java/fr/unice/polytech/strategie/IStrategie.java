package fr.unice.polytech.strategie;

import fr.unice.polytech.CarteQuartier;

import java.util.ArrayList;
import java.util.Random;

public interface IStrategie {

    default CarteQuartier quartierAConstruire(ArrayList<CarteQuartier> quartiers) {
        return quartiers.get(new Random().nextInt(0, quartiers.size()));
    }
}
