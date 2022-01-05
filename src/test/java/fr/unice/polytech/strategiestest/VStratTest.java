package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Collections;
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

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void cibleBeaucoupCartesMain() {
        Joueur tropDeCartes = joueurs.get(0);
        ArrayList<CarteQuartier> mainVide = new ArrayList<>();
        vinh.setQuartiers(mainVide);
        vinh.piocherPersonnage();
        assertEquals("Magicien", vinh.getPersonnage().getNom());
        tropDeCartes.ajouterQuartierEnMain();
        tropDeCartes.piocherPersonnage();
        Joueur cible = vinh.getStrategie().getIStrategie().choixDeCibleJoueur(vinh,joueurs);
        assertEquals(tropDeCartes, cible);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void ciblePlusDePoints() {
        Joueur sixQuartiersConstruits = joueurs.get(0);
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.setQuartiersConstruits(sixQuartiersConstruits.getQuartiers());
        assertEquals(6, sixQuartiersConstruits.getQuartiersConstruits().size());
        vinh.piocherPersonnage();
        Joueur autre = joueurs.get(1);
        autre.ajouteOr(4);
        autre.construireQuartier();
        Joueur cible = vinh.getStrategie().getIStrategie().choixDeCibleJoueur(vinh,joueurs);
        assertEquals(sixQuartiersConstruits, cible);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixQuartierMoinsCher() {
        vinh.piocherPersonnage();
        CarteQuartier moinsCher = vinh.getStrategie().getIStrategie().choixDeQuartier(vinh, vinh.getQuartiers());
        Collections.sort(vinh.getQuartiers());
        assertEquals(vinh.getQuartiers().get(vinh.getQuartiers().size()-1), moinsCher);
    }






}
