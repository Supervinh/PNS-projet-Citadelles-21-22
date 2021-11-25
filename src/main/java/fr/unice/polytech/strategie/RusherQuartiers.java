package fr.unice.polytech.strategie;

import fr.unice.polytech.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;

public class RusherQuartiers implements IStrategie {

    @Override
    public CarteQuartier quartierAConstruire(ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers);
        Collections.sort(carteQuartiers);
        return carteQuartiers.get(0);
    }
}
