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

        // Choix de Cible utilisant un Joueur
        Joueur cible = cibleAleatoire();

        CarteQuartier quartierDetruit = choixQuartierAleatoire(joueur, cible);
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        if (quartierDetruit != null) {
            cible.getQuartiersConstruits().remove(quartierDetruit);
            MoteurDeJeu.deck.ajouterQuartierDeck(quartierDetruit);
            joueur.ajouteOr(-quartierDetruit.getPrix() + 1);
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a détruit le quartier " + CouleurConsole.printGreen(quartierDetruit.getNom()) + " de " + cible.getNom());
        } else {
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " n'a pas détruit de quartier.");
        }
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
