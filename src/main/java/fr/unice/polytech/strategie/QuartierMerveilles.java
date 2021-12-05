package fr.unice.polytech.strategie;

import fr.unice.polytech.CarteQuartier;

import java.util.ArrayList;

public class QuartierMerveilles implements IStrategie {

    @Override
    public CarteQuartier quartierAConstruire(ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers.stream().filter(cq -> cq.getGemme().equals("Prestige")).toList());
        if (carteQuartiers.size() > 0) {
            return carteQuartiers.get(carteQuartiers.size() - 1);
        } else {
            return IStrategie.super.quartierAConstruire(quartiers);
        }
    }
}
