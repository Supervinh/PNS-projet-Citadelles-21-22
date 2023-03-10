package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AgressifTest {
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur agressif;

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        joueurs.get(2).getStrategie().setStrategie("Agressif");
        m.setJoueurs(joueurs);
        agressif = joueurs.get(2);
    }

    @Test
    void choixAssassin() {
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
    }

    @Test
    void choixCondottiere() {
        Joueur sixQuartiersConstruits = joueurs.get(0);
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.setQuartiersConstruits(sixQuartiersConstruits.getQuartiers());
        assertEquals(6, sixQuartiersConstruits.getQuartiersConstruits().size());
        agressif.piocherPersonnage();
        assertEquals("Condottiere", agressif.getPersonnage().getNom());
    }

    @Test
    void choixMagicien() {
        ArrayList<CarteQuartier> mainVide = new ArrayList<>();
        agressif.setQuartiers(mainVide);
        agressif.piocherPersonnage();
        assertEquals("Magicien", agressif.getPersonnage().getNom());
    }

    @Test
    void choixVoleur() {
        Joueur tropRiche = joueurs.get(0);
        tropRiche.ajouteOr(6);
        agressif.piocherPersonnage();
        assertEquals("Voleur", agressif.getPersonnage().getNom());
    }

    @Test
    void choixAutre() {
        MoteurDeJeu.deck.getPersonnages().removeIf(p -> p.getNom().equals("Assassin") || p.getNom().equals("Voleur") || p.getNom().equals("Condottiere") || p.getNom().equals("Magicien"));
        agressif.piocherPersonnage();
        assertNotEquals("Assassin", agressif.getPersonnage().getNom());
        assertNotEquals("Voleur", agressif.getPersonnage().getNom());
        assertNotEquals("Magicien", agressif.getPersonnage().getNom());
        assertNotEquals("Condottiere", agressif.getPersonnage().getNom());
    }

    @Test
    void tuerVoleur() {
        Joueur riche = joueurs.get(0);
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
        riche.ajouteOr(6);
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Voleur", cible.getNom());
    }

    @Test
    void tuerCondottiere() {
        Joueur bientotGagnant = joueurs.get(0);
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
        ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
        for (int i = 0; i < 7; i++) quartiersConstruits.add(bientotGagnant.piocherQuartier());
        bientotGagnant.setQuartiersConstruits(quartiersConstruits);
        assertEquals(7, bientotGagnant.getQuartiersConstruits().size());
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Condottiere", cible.getNom());
    }

    @Test
    void tuerCondottiereBis() {
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
        ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
        for (int i = 0; i < 6; i++) quartiersConstruits.add(agressif.piocherQuartier());
        agressif.setQuartiersConstruits(quartiersConstruits);
        assertEquals(6, agressif.getQuartiersConstruits().size());
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Condottiere", cible.getNom());
    }

    @Test
    void tuerRoi() {
        Joueur roi = joueurs.get(0);
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
        ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
        for (int i = 0; i < 6; i++) quartiersConstruits.add(roi.piocherQuartier());
        roi.setQuartiersConstruits(quartiersConstruits);
        assertEquals(6, roi.getQuartiersConstruits().size());
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Roi", cible.getNom());
    }

    @Test
    void tuerArchitecte() {
        Joueur architecte = joueurs.get(0);
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
        ArrayList<CarteQuartier> quartiersConstruits = new ArrayList<>();
        for (int i = 0; i < 5; i++) quartiersConstruits.add(architecte.piocherQuartier());
        architecte.setQuartiersConstruits(quartiersConstruits);
        assertEquals(5, architecte.getQuartiersConstruits().size());
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte", cible.getNom());
    }

    @Test
    void tuerArchitecteBis() {
        Joueur architecte = joueurs.get(0);
        agressif.piocherPersonnage();
        assertEquals("Assassin", agressif.getPersonnage().getNom());
        architecte.ajouteOr(4);
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte", cible.getNom());
    }

    @Test
    void volerArchitecteBis() {
        Joueur tropRiche = joueurs.get(0);
        tropRiche.ajouteOr(6);
        agressif.piocherPersonnage();
        assertEquals("Voleur", agressif.getPersonnage().getNom());
        CartePersonnage cible = agressif.getStrategie().getIStrategie().choixDeCibleCartePersonnage(agressif, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte", cible.getNom());
    }

    @Test
    void cibleBeaucoupCartesMain() {
        Joueur tropDeCartes = joueurs.get(0);
        ArrayList<CarteQuartier> mainVide = new ArrayList<>();
        agressif.setQuartiers(mainVide);
        agressif.piocherPersonnage();
        assertEquals("Magicien", agressif.getPersonnage().getNom());
        tropDeCartes.ajouterQuartierEnMain();
        tropDeCartes.piocherPersonnage();
        Joueur cible = agressif.getStrategie().getIStrategie().choixDeCibleJoueur(agressif, joueurs);
        assertEquals(tropDeCartes, cible);
    }

    @Test
    void ciblePlusDePoints() {
        Joueur sixQuartiersConstruits = joueurs.get(0);
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.ajouterQuartierEnMain();
        sixQuartiersConstruits.setQuartiersConstruits(sixQuartiersConstruits.getQuartiers());
        assertEquals(6, sixQuartiersConstruits.getQuartiersConstruits().size());
        agressif.piocherPersonnage();
        assertEquals("Condottiere", agressif.getPersonnage().getNom());
        Joueur autre = joueurs.get(1);
        autre.ajouteOr(4);
        autre.construireQuartier();
        Joueur cible = agressif.getStrategie().getIStrategie().choixDeCibleJoueur(agressif, joueurs);
        assertEquals(sixQuartiersConstruits, cible);
    }

    @Test
    void choixQuartierMoinsCher() {
        agressif.piocherPersonnage();
        CarteQuartier moinsCher = agressif.getStrategie().getIStrategie().choixDeQuartier(agressif, agressif.getQuartiers());
        Collections.sort(agressif.getQuartiers());
        assertEquals(agressif.getQuartiers().get(agressif.getQuartiers().size() - 1), moinsCher);
    }

}
