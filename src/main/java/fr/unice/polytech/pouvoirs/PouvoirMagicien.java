package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PouvoirMagicien implements IPouvoir {

    @Override
    public void utiliserPouvoir(Joueur joueur) {

        // Choix de Cible utilisant un nom de Personnage
        ArrayList<CartePersonnage> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.deck.getPersonnagesPossibles()));
        cibles.remove(joueur.getPersonnage());
        CartePersonnage cibleNomPersonnage = cibles.get(new Random().nextInt(cibles.size()));

        // Si Cible est attribuée a un Joueur ou pas
        Joueur cible = MoteurDeJeu.joueurs.stream()
                .filter(j -> j.getPersonnage().equals(cibleNomPersonnage))
                .findFirst()
                .orElse(null);


        if (cible != null) {
            ArrayList<CarteQuartier> temporaire = new ArrayList<>();

            temporaire = joueur.getQuartiers();
            joueur.setQuartiers(cible.getQuartiers());
            cible.setQuartiers(temporaire);

            System.out.println(joueur.getNom() + " a échangé ses cartes avec " + cibleNomPersonnage.getNom());

        } else {
            System.out.println(joueur.getNom() + " a essayé d'échanger ses cartes avec " + cibleNomPersonnage.getNom());
        }


    }
}
