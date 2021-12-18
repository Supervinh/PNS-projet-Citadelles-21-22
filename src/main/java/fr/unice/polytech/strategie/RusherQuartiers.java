package fr.unice.polytech.strategie;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;

import java.util.ArrayList;
import java.util.Collections;

public class RusherQuartiers implements IStrategie {

    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = personnages.stream().filter(cp -> cp.getNom().equals("Architecte")).findAny().orElseGet(
                () -> IStrategie.super.choixDePersonnage(joueur, personnages));
        if (joueur.getOr() < 3) {
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(choix);
        }
        return choix;
    }

    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        Joueur cibleJoueur = MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElse(null);
        if (cibleJoueur != null) {
            return cibleJoueur.getStrategie().getIStrategie().choixDePersonnage(joueur, MoteurDeJeu.deck.getPersonnagesPossibles());
        } else {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
    }

    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
    }

    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers);
        Collections.sort(carteQuartiers);
        return carteQuartiers.get(carteQuartiers.size() - 1);
    }

    @Override
    public String nomStrategie() {
        return "Quartiers les moins cher";
    }
}
