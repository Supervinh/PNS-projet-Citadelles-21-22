package fr.unice.polytech.strategie;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;

public class VStrat implements IStrategie {

    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = null;
        int quartierReligieux = 0;
        for (int i = 0; i < joueur.getQuartiersConstruits().size(); i++) {
            if (joueur.getQuartiersConstruits().get(i).getGemme().equals("Religion")) quartierReligieux++;
        }
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        if (joueur.getQuartiers().size() == 0)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Magicien")).findAny().orElse(null);
        if (joueur.getQuartiers().size() > 2 && joueur.getOr() > 5 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Architecte")).findAny().orElse(null);
        if (orMax > 4 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(null);
        if (quartierReligieux > 1 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Évêque")).findAny().orElse(null);
        if (choix == null) choix = personnages.stream().filter(cp -> cp.getNom().equals("Marchand")).findAny().orElseGet(() -> IStrategie.super.choixDePersonnage(joueur, personnages));
        return choix;
    }

    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        CartePersonnage personnageCible = null;
        if (joueur.getPersonnage().getNom().equals("Assassin")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Magicien")).findFirst().orElse(null);
        } else if (joueur.getPersonnage().getNom().equals("Voleur")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Marchand")).findFirst().orElse(null);
        }
        if (personnageCible == null) {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
        return personnageCible;
    }

    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        if (joueur.getPersonnage().getNom().equals("Magicien")) {
            int nbreQuartierMain = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).map(Joueur::getQuartiers).mapToInt(ArrayList::size).max().orElse(0);
            return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == nbreQuartierMain).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
        }
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
        return "Stratégie spéciale Vinh";
    }
}
