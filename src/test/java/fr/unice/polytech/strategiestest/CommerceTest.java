package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.Commerce;
import fr.unice.polytech.strategie.RusherQuartiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CommerceTest {

    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur commerce;
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();

    @BeforeEach
    void setUp(){
        m = new MoteurDeJeu();
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs,true);
        m.setJoueurs(joueurs);
        commerce = joueurs.get(1);
        commerce.getStrategie().setStrategie("Commerce et Artisanat");

    }


    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixArchitecte(){
        CarteQuartier carte1 = new CarteQuartier(15.1,"Prison","Soldatesque",2);
        CarteQuartier carte2 = new CarteQuartier(1.1,"Temple","Religion",1);

        commerce.ajouteOr(6);

        commerce.getQuartiers().add(carte1);
        commerce.getQuartiers().add(carte2);

        commerce.piocherPersonnage();
        assertEquals("Architecte", commerce.getPersonnage().getNom());
    }


    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixCibleNotVoleur(){
        CartePersonnage personnage = new CartePersonnage(1, "Assassin",null);
        commerce.setPersonnage(personnage);
        CartePersonnage cible = commerce.getStrategie().getIStrategie().choixDeCibleCartePersonnage(commerce,MoteurDeJeu.deck.getPersonnages());
        assertNotEquals("Architecte", cible.getNom());
    }


    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixQuartierCommerce(){
        CarteQuartier carte = new CarteQuartier(10.1, "Marche","Commerce et Artisanat",2);
        quartiers.add(carte);

        Commerce pouvoir = Mockito.mock(Commerce.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier commerceQuartier = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartiers.get(0), commerceQuartier);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixQuartierNotCommerce(){
        CarteQuartier carte = new CarteQuartier(15.1,"Prison","Soldatesque",2);
        quartiers.add(carte);

        Commerce pouvoir = Mockito.mock(Commerce.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier commerceQuartier = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartiers.get(0), commerceQuartier);
    }
}
