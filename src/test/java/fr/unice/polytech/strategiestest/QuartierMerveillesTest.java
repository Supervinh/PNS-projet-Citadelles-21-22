package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.QuartierMerveilles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuartierMerveillesTest {
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur jQuartier;
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        jQuartier = joueurs.get(1);
        jQuartier.getStrategie().setStrategie("Merveille");
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
        quartier = new CarteQuartier(18, "Cour des miracles", "Prestige", 5);
        quartiers.add(quartier);
    }

    @Test
    void cartePrestige() {
        QuartierMerveilles pouvoir = Mockito.mock(QuartierMerveilles.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier quartierPrestige = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartier, quartierPrestige);
    }

    @Test
    void piocherPersonnage(){
        jQuartier.piocherPersonnage();
        assertEquals(CartePersonnage.class,jQuartier.getPersonnage().getClass());
        assertNotEquals(null,jQuartier);
    }

    @Test
    void ciblerPersonnage(){
        QuartierMerveilles pouvoir = Mockito.mock(QuartierMerveilles.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleCartePersonnage(jQuartier, MoteurDeJeu.deck.getPersonnages());
        CartePersonnage personnageCible = pouvoir.choixDeCibleCartePersonnage(jQuartier, MoteurDeJeu.deck.getPersonnages());
        assertEquals(CartePersonnage.class,personnageCible.getClass());
        assertNotEquals(null,personnageCible);
    }

    @Test
    void ciblerJoueur(){
        QuartierMerveilles pouvoir = Mockito.mock(QuartierMerveilles.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleJoueur(jQuartier,joueurs);
        Joueur joueurCible = pouvoir.choixDeCibleJoueur(jQuartier, joueurs);
        assertEquals(Joueur.class,joueurCible.getClass());
        assertNotEquals(null,joueurCible);
    }
}
