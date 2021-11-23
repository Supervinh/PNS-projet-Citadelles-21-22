package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PouvoirCondottiere implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        // Choix de Cible utilisant un Joueur
        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.joueurs));
        cibles.removeIf(j -> j.equals(joueur) || j.getPersonnage().getNom().equals("Évêque") || j.getQuartiersConstruits().size() <= 0 || j.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire);
        Joueur cible = cibles.get(new Random().nextInt(cibles.size()));

        Random r = new Random();
        int numQuartier = r.nextInt(cible.getQuartiersConstruits().size());
        CarteQuartier quartierDetruit = cible.getQuartiersConstruits().remove(numQuartier);

        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a détruit le quartier " + CouleurConsole.printGreen(quartierDetruit.getNom()) + " de " + cible.getNom());
        this.recupererTaxes(joueur);
    }

    public void recupererTaxes(Joueur joueur) {
        System.out.println(CouleurConsole.printRed("| ") + "\n" + CouleurConsole.printRed("| ") + "Recuperation des " + CouleurConsole.printGold("Taxes") + " des Gemmes " + CouleurConsole.printPurple(joueur.getPersonnage().getGemme()));
        int count = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals(joueur.getPersonnage().getGemme())).count();
        joueur.ajouteOr(count);
        boolean plurielle = count > 1;
        System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a pioché " + CouleurConsole.printGold("" + count) + " pièce" + (plurielle ? "s" : "") + " d'" + CouleurConsole.printGold("or") + " supplémentaire" + (plurielle ? "s" : "") + ".");
    }
}
