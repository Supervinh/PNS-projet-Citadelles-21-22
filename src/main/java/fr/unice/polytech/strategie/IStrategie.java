package fr.unice.polytech.strategie;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;

import java.util.ArrayList;
import java.util.Random;

public interface IStrategie {

    default CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        ArrayList<CartePersonnage> personnageTemp = new ArrayList<>(personnages);
        if (personnageTemp.size() > 0) {
            return personnageTemp.get(new Random().nextInt(personnageTemp.size()));
        } else {
            return null;
        }
    }

    default CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> cibles) {
        ArrayList<CartePersonnage> ciblesTemp = new ArrayList<>(cibles);
        ciblesTemp.remove(joueur.getPersonnage());
        if (ciblesTemp.size() > 0) {
            return ciblesTemp.get(new Random().nextInt(ciblesTemp.size()));
        } else {
            return null;
        }
    }

    default Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        ArrayList<Joueur> ciblesTemp = new ArrayList<>(cibles);
        ciblesTemp.remove(joueur);
        if (ciblesTemp.size() > 0) {
            return ciblesTemp.get(new Random().nextInt(ciblesTemp.size()));
        } else {
            return null;
        }
    }

    default CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> quartiersTemp = new ArrayList<>(quartiers);
        joueur.getQuartiersConstruits().forEach(quartiersTemp::remove);
        if (quartiersTemp.size() > 0) {
            return quartiersTemp.get(new Random().nextInt(quartiersTemp.size()));
        } else {
            return null;
        }
    }

    default String nomStrategie() {
        return "Par Default (Random)";
    }
}
