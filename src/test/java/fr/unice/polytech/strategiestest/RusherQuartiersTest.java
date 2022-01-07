package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.Opportuniste;
import fr.unice.polytech.strategie.RusherQuartiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RusherQuartiersTest {
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur jRush;

    @BeforeEach
    void setUp() {
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        jRush = joueurs.get(1);
        jRush.getStrategie().setStrategie("Rusher");
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
        quartier = new CarteQuartier(18, "Cour des miracles", "Prestige", 5);
        quartiers.add(quartier);
    }


    @Test
    void carteRush() {
        RusherQuartiers pouvoir = Mockito.mock(RusherQuartiers.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(null, quartiers);
        CarteQuartier carteRusher = pouvoir.choixDeQuartier(null, quartiers);
        assertEquals(quartiers.get(0), carteRusher);
    }

    @Test
    void choixMaxPoint() {
        joueurs.get(0).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 3));
        joueurs.get(2).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        RusherQuartiers pouvoir = Mockito.mock(RusherQuartiers.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleJoueur(jRush, joueurs);
        Joueur joueurCible = pouvoir.choixDeCibleJoueur(jRush, joueurs);
        assertEquals(joueurs.get(0), joueurCible);
    }

    @Test
    void choixCibleArchitecte() {
        jRush.setPersonnage(new CartePersonnage(1, "Voleur", null));
        RusherQuartiers pouvoir = Mockito.mock(RusherQuartiers.class);
        MoteurDeJeu.deck.getPersonnages().removeIf(cartePersonnage -> cartePersonnage.getNom().equals("Marchand"));
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleCartePersonnage(jRush, MoteurDeJeu.deck.getPersonnages());
        CartePersonnage personnageCible = pouvoir.choixDeCibleCartePersonnage(jRush, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte", personnageCible.getNom());
    }

    @Test
    void choixArchitecte(){
        jRush.ajouteOr(4);
        jRush.piocherPersonnage();
        assertEquals("Architecte", jRush.getPersonnage().getNom());
    }

    @Test
    void choixVoleur(){
        jRush.ajouteOr(-jRush.getOr());
        jRush.piocherPersonnage();
        assertEquals("Voleur",jRush.getPersonnage().getNom());
    }
}
