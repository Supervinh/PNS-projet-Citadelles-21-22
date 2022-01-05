package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpportunisteTest {
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur opportuniste;

    @BeforeEach
    void setUp() {
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        opportuniste = joueurs.get(1);
        opportuniste.getStrategie().setStrategie("Opportuniste");
    }

    @Test
    void choixCondottiere() {
        opportuniste.piocherPersonnage();
        assertEquals("Condottiere", opportuniste.getPersonnage().getNom());
    }

    @Test
    void choixEveque() {
        CarteQuartier carteReligion = new CarteQuartier(3.1, "Temple", "Religion", 1);
        opportuniste.getQuartiersConstruits().add(carteReligion);
        opportuniste.piocherPersonnage();
        assertEquals("Évêque", opportuniste.getPersonnage().getNom());
    }

    @Test
    void choixVoleur() {
        opportuniste.ajouteOr(-opportuniste.getOr());
        joueurs.get(3).ajouteOr(4);
        opportuniste.piocherPersonnage();
        assertEquals("Voleur", opportuniste.getPersonnage().getNom());
    }
}
