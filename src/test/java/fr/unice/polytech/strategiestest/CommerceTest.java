package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.Batisseur;
import fr.unice.polytech.strategie.Commerce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        commerce.getStrategie().setStrategie("Commerce");
    }

    @Test
    void choixVoleur(){
        Joueur tropRiche = joueurs.get(0);
        tropRiche.ajouteOr(5);
        commerce.piocherPersonnage();
        assertEquals("Voleur", commerce.getPersonnage().getNom());
    }

    @Test
    void choixArchitecte(){
        CarteQuartier carte1 = new CarteQuartier(15.1,"Prison","Soldatesque",2);
        CarteQuartier carte2 = new CarteQuartier(1.1,"Temple","Religion",1);

        commerce.ajouteOr(6);

        commerce.getQuartiers().add(carte1);
        commerce.getQuartiers().add(carte2);

        commerce.piocherPersonnage();
        assertEquals("Architecte", commerce.getPersonnage().getNom());
    }


    @Test
    void choixCibleVoleur(){
        commerce.setPersonnage(new CartePersonnage(2,"Voleur",null));
        Commerce pouvoir = Mockito.mock(Commerce.class);
        MoteurDeJeu.deck.getPersonnages().removeIf(cartePersonnage -> cartePersonnage.getNom().equals("Marchand"));
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleCartePersonnage(commerce, MoteurDeJeu.deck.getPersonnages());
        CartePersonnage personnageCible = pouvoir.choixDeCibleCartePersonnage(commerce, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte",personnageCible.getNom());
    }

    @Test
    void choixCibleNotVoleur(){
        commerce.setPersonnage(new CartePersonnage(1,"Voleur",null));
        Commerce pouvoir = Mockito.mock(Commerce.class);
        MoteurDeJeu.deck.getPersonnages().removeIf(cartePersonnage -> cartePersonnage.getNom().equals("Marchand"));
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleCartePersonnage(commerce, MoteurDeJeu.deck.getPersonnages());
        CartePersonnage personnageCible = pouvoir.choixDeCibleCartePersonnage(commerce, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte",personnageCible.getNom());
    }


    @Test
    void choixQuartierCommerce(){
        CarteQuartier carte = new CarteQuartier(10.1, "Marche","Commerce et Artisanat",2);
        quartiers.add(carte);

        Commerce pouvoir = Mockito.mock(Commerce.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier commerceQuartier = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartiers.get(0), commerceQuartier);
    }

    @Test
    void choixQuartierNotCommerce(){
        CarteQuartier carte = new CarteQuartier(15.1,"Prison","Soldatesque",2);
        quartiers.add(carte);

        Commerce pouvoir = Mockito.mock(Commerce.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier commerceQuartier = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartiers.get(0), commerceQuartier);
    }

    @Test
    void choixMaxPoint(){
        joueurs.get(0).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 3));
        joueurs.get(2).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        Commerce pouvoir = Mockito.mock(Commerce.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleJoueur(commerce, joueurs);
        Joueur joueurCible = pouvoir.choixDeCibleJoueur(commerce, joueurs);
        assertEquals(joueurs.get(0),joueurCible);
    }


}
