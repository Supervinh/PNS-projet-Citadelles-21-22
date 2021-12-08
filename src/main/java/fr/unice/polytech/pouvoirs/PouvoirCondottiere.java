package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirCondottiere implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {
        Joueur cible = cibleAleatoire();
        CarteQuartier quartierDetruit = choixQuartierAleatoire(joueur, cible);
        System.out.println(CouleurConsole.printRed("| Pouvoir ") + joueur.getPersonnage().getNomColoured());
        if (quartierDetruit != null) {
            cible.getQuartiersConstruits().remove(quartierDetruit);
            MoteurDeJeu.deck.ajouterQuartierDeck(quartierDetruit);
            joueur.ajouteOr(1 - quartierDetruit.getPrix());
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " a détruit le quartier " + quartierDetruit.getNomColoured() + " de " + cible.getNomColoured());
        } else {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNomColoured() + " n'a pas détruit de quartier.");
        }
        System.out.println(CouleurConsole.printRed("|"));
        this.recupererTaxes(joueur);
    }

    public Joueur cibleAleatoire() {
        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.personnagesConnus));
        cibles.removeIf(j -> (j.getPersonnage().getNom().equals("Évêque") && !j.isMort()) || j.getQuartiersConstruits().size() <= 0 || j.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire);
        return cibles.get(new Random().nextInt(cibles.size()));
    }

    public CarteQuartier choixQuartierAleatoire(Joueur joueur, Joueur cible) {
        ArrayList<CarteQuartier> cibesPossible = new ArrayList<>(cible.getQuartiersConstruits().stream().filter(cq -> !cq.getNom().equals("Donjon") && hasEnoughMoney(joueur, cq)).toList());
        if (cibesPossible.size() > 0) {
            return cibesPossible.get(new Random().nextInt(cibesPossible.size()));
        } else {
            return null;
        }
    }

    public boolean hasEnoughMoney(Joueur joueur, CarteQuartier quartier) {
        return (joueur.getOr() >= quartier.getPrix() - 1);
    }
}
