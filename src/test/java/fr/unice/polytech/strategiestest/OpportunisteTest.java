package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.Opportuniste;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OpportunisteTest {
    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur opportuniste;

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m = new MoteurDeJeu();
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        opportuniste = joueurs.get(1);
        opportuniste.getStrategie().setStrategie("Opportuniste");
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixCondottiere() {
        opportuniste.piocherPersonnage();
        assertEquals("Condottiere", opportuniste.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixEveque() {
        CarteQuartier carteReligion = new CarteQuartier(3.1, "Temple", "Religion", 1);
        opportuniste.getQuartiersConstruits().add(carteReligion);
        opportuniste.piocherPersonnage();
        assertEquals("Évêque", opportuniste.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixVoleur() {
        opportuniste.ajouteOr(-opportuniste.getOr());
        joueurs.get(3).ajouteOr(4);
        opportuniste.piocherPersonnage();
        assertEquals("Voleur", opportuniste.getPersonnage().getNom());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixQuartierReligion() {
        ArrayList<CarteQuartier> quartiers = new ArrayList<>();
        quartiers.add(new CarteQuartier(5.1, "Manoir", "Noblesse", 3));
        quartiers.add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        quartiers.add(new CarteQuartier(3.2, "Tour de guet", "Soldatesque", 1));
        Opportuniste pouvoir = Mockito.mock(Opportuniste.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(opportuniste, quartiers);
        CarteQuartier carteNoblesse = pouvoir.choixDeQuartier(opportuniste, quartiers);
        assertEquals("Religion", carteNoblesse.getGemme());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixParDefaut() {
        ArrayList<CarteQuartier> quartiers = new ArrayList<>();
        quartiers.add(new CarteQuartier(5.1, "Manoir", "Noblesse", 3));
        quartiers.add(new CarteQuartier(3.2, "Tour de guet", "Soldatesque", 1));
        Opportuniste pouvoir = Mockito.mock(Opportuniste.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(opportuniste, quartiers);
        CarteQuartier carte = pouvoir.choixDeQuartier(opportuniste, quartiers);
        assertNotEquals("Religion", carte.getGemme());
        assertEquals("Soldatesque", carte.getGemme());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixMaxPoint() {
        joueurs.get(0).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 3));
        joueurs.get(2).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        Opportuniste pouvoir = Mockito.mock(Opportuniste.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleJoueur(opportuniste, joueurs);
        Joueur joueurCible = pouvoir.choixDeCibleJoueur(opportuniste, joueurs);
        assertEquals(joueurs.get(0), joueurCible);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void choixCibleArchitecte() {
        opportuniste.setPersonnage(new CartePersonnage(1, "Voleur", null));
        Opportuniste pouvoir = Mockito.mock(Opportuniste.class);
        MoteurDeJeu.deck.getPersonnages().removeIf(cartePersonnage -> cartePersonnage.getNom().equals("Marchand"));
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleCartePersonnage(opportuniste, MoteurDeJeu.deck.getPersonnages());
        CartePersonnage personnageCible = pouvoir.choixDeCibleCartePersonnage(opportuniste, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte", personnageCible.getNom());
    }
}
