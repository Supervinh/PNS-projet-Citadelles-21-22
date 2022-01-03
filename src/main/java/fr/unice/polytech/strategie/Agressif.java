package fr.unice.polytech.strategie;

import fr.unice.polytech.*;

import java.util.ArrayList;
import java.util.Collections;

public class Agressif implements IStrategie {

    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = personnages.stream().filter(cp -> cp.getNom().equals("Assassin") || cp.getNom().equals("Voleur")).findAny().orElseGet(
                () -> IStrategie.super.choixDePersonnage(joueur, personnages));
        return choix;
    }

    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        CartePersonnage personnageCible = null;
        if (joueur.getPersonnage().getNom().equals("Assassin")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Magicien")).findFirst().orElse(null);
        }
        else if (joueur.getPersonnage().getNom().equals("Voleur")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Marchand")).findFirst().orElse(null);
        }
        if (personnageCible == null) {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
        return personnageCible;
    }

    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        int nbreQuartierMain = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiers).mapToInt(ArrayList::size).max().orElse(0);
        if (nbreQuartierMain>4) return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == nbreQuartierMain).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
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
        return "Stratégie aggressive afin de gêner au plus les adversaires";
    }
}
