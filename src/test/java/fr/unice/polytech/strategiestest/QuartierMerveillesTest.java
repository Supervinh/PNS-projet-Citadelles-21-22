package fr.unice.polytech.strategiestest;

import fr.unice.polytech.*;
import fr.unice.polytech.pouvoirs.PouvoirCondottiere;
import fr.unice.polytech.strategie.QuartierMerveilles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuartierMerveillesTest {
    Joueur joueur;
    MoteurDeJeu moteurDeJeu;
    Strategie strategie;
    ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        /*moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs);
        joueur = joueurs.get(0);
        joueur.setPersonnage(new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or."));
        */
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
        quartier = new CarteQuartier(18, "Cour des miracles", "Prestige", 5);
        quartiers.add(quartier);
        /*strategie = new Strategie(joueur);
        strategie.setStrategie("Merveille");*/
    }


    @Test
    void cartePrestige (){
        QuartierMerveilles pouvoir = Mockito.mock(QuartierMerveilles.class);
        Mockito.doCallRealMethod().when(pouvoir).quartierAConstruire(quartiers);
        CarteQuartier quartierPrestige = pouvoir.quartierAConstruire(quartiers);
        assertEquals(quartier, quartierPrestige);
    }
}
