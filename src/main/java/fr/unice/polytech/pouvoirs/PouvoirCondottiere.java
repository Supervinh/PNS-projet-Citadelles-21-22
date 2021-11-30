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
        ArrayList<Joueur> cibles = new ArrayList<>(List.copyOf(MoteurDeJeu.joueurs));
        cibles.removeIf(j -> j.equals(joueur) || j.getPersonnage().getNom().equals("Évêque") || j.getQuartiersConstruits().size() <= 0 || j.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire );
        Joueur cible = cibles.get(new Random().nextInt(cibles.size()));

        Random r = new Random();
        int numQuartier = r.nextInt(cible.getQuartiersConstruits().size());
        CarteQuartier quartierDetruit=cible.getQuartiersConstruits().get(numQuartier);
        System.out.println(CouleurConsole.printRed("| Pouvoir " + joueur.getPersonnage().getNom()));
        if(hasEnoughMoney(joueur,quartierDetruit)){
            cible.getQuartiersConstruits().remove(numQuartier);
            joueur.setOr(-quartierDetruit.getPrix()+1);
            System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " a détruit le quartier " + CouleurConsole.printGreen(quartierDetruit.getNom()) + " de " + cible.getNom());
        }
        else{System.out.println(CouleurConsole.printRed("| ") + joueur.getNom() + " n'a pas détruit de quartier.");}
        this.recupererTaxes(joueur);
    }

    public boolean hasEnoughMoney(Joueur joueur, CarteQuartier quartier){
        return(joueur.getOr()>= quartier.getPrix()-1);
    }
}
