package fr.unice.polytech.strategie;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;

import java.util.ArrayList;

public class QuartierMerveilles implements IStrategie {

    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        return IStrategie.super.choixDePersonnage(joueur, personnages);
    }

    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
    }

    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        return IStrategie.super.choixDeCibleJoueur(joueur, cibles);
    }

    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers.stream().filter(cq -> cq.getGemme().equals("Prestige")).toList());
        if (carteQuartiers.size() > 0) {
            return carteQuartiers.get(carteQuartiers.size() - 1);
        } else {
            return IStrategie.super.choixDeQuartier(joueur, quartiers);
        }
    }

    @Override
    public String nomStrategie() {
        return "Quartiers Merveilles";
    }
}
