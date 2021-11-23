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

        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.joueurs));
        cibles.remove(joueur);
        Joueur cible = cibles.get(new Random().nextInt(cibles.size()));


        while (cible.getPersonnage().getNom().equals("Évêque") || cible.getQuartiersConstruits().size() == 0 || cible.getQuartiersConstruits().size() == 8) {
            cible = cibles.get(new Random().nextInt(cibles.size()));
        }
        Random r = new Random();
        int numquartier = r.nextInt(cible.getQuartiersConstruits().size());
        CarteQuartier quartierdetruit = cible.getQuartiersConstruits().remove(numquartier);
        System.out.println(joueur.getNom() + " a détruit le quartier " + CouleurConsole.printGreen(quartierdetruit.getNom()) + " de " + cible.getNom());
    }
}
