package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VStratTest {
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur vinh;

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        vinh = joueurs.get(3);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixMagicien() {
        ArrayList<CarteQuartier> mainVide = new ArrayList<>();
        vinh.setQuartiers(mainVide);
        vinh.piocherPersonnage();
        assertEquals("Magicien", vinh.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixArchitecte() {
        vinh.ajouteOr(4);
        vinh.piocherPersonnage();
        assertEquals("Architecte", vinh.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixVoleur() {
        Joueur tropRiche = joueurs.get(0);
        tropRiche.ajouteOr(3);
        vinh.piocherPersonnage();
        assertEquals("Voleur", vinh.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixRoi() {
        vinh.getQuartiers().add(vinh.piocherQuartier());
        vinh.setQuartiersConstruits(vinh.getQuartiers());
        vinh.ajouteOr(4);
        vinh.piocherPersonnage();
        assertEquals("Roi", vinh.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixEveque() {
        vinh.getQuartiers().clear();
        MoteurDeJeu.deck.getQuartiers().removeIf(q -> !q.getGemme().equals("Religion"));
        vinh.getQuartiers().add(vinh.piocherQuartier());
        vinh.getQuartiers().add(vinh.piocherQuartier());
        vinh.setQuartiersConstruits(vinh.getQuartiers());
        vinh.piocherPersonnage();
        assertEquals("Évêque", vinh.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixMarchand() {
        vinh.piocherPersonnage();
        assertEquals("Marchand", vinh.getPersonnage().getNom());
    }





}
