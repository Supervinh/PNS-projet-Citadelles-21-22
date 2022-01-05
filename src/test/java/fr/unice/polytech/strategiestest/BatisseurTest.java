package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatisseurTest {

    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur batisseur;

    @BeforeEach
    void setUp(){
        m = new MoteurDeJeu();
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs,true);
        m.setJoueurs(joueurs);
        batisseur = joueurs.get(1);
        batisseur.getStrategie().setStrategie("Batisseur");

    }

    @Test
    void choixRoi(){
        CarteQuartier carteNoblesse = new CarteQuartier(5.1,"Manoir","Noblesse",3);
        batisseur.getQuartiersConstruits().add(carteNoblesse);
        batisseur.piocherPersonnage();
        assertEquals("Roi", batisseur.getPersonnage().getNom());
    }

    @Test
    void choixMarchand(){
        CarteQuartier carteCommerce = new CarteQuartier(5.1,"Taverne","Commerce et Artisanat",3);
        batisseur.getQuartiersConstruits().add(carteCommerce);
        batisseur.piocherPersonnage();
        assertEquals("Marchand", batisseur.getPersonnage().getNom());
    }

    @Test
    void choixArchitecte(){
        batisseur.ajouteOr(-batisseur.getOr());
        batisseur.ajouteOr(5);
        batisseur.piocherPersonnage();
        assertEquals("Architecte", batisseur.getPersonnage().getNom());
    }

}
