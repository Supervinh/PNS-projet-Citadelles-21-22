package fr.unice.polytech.strategiestest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.strategie.Batisseur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BatisseurTest {

    MoteurDeJeu m;
    ArrayList<Joueur> joueurs;
    Joueur batisseur;

    @BeforeEach
    void setUp() {
        m = new MoteurDeJeu();
        MoteurDeJeu.setMessageLvl(Level.OFF);
        m.setNbJoueurs(5);
        joueurs = new ArrayList<>();
        m.initialiseJoueurs(joueurs, true);
        m.setJoueurs(joueurs);
        batisseur = joueurs.get(1);
        batisseur.getStrategie().setStrategie("Batisseur");

    }

    @Test
    void choixRoi() {
        CarteQuartier carteNoblesse = new CarteQuartier(5.1, "Manoir", "Noblesse", 3);
        batisseur.getQuartiersConstruits().add(carteNoblesse);
        batisseur.piocherPersonnage();
        assertEquals("Roi", batisseur.getPersonnage().getNom());
    }

    @Test
    void choixMarchand() {
        CarteQuartier carteCommerce = new CarteQuartier(5.1, "Taverne", "Commerce et Artisanat", 3);
        batisseur.getQuartiersConstruits().add(carteCommerce);
        batisseur.piocherPersonnage();
        assertEquals("Marchand", batisseur.getPersonnage().getNom());
    }

    @Test
    void choixArchitecte() {
        batisseur.ajouteOr(-batisseur.getOr());
        batisseur.ajouteOr(5);
        batisseur.piocherPersonnage();
        assertEquals("Architecte", batisseur.getPersonnage().getNom());
    }

    @Test
    void choixQuartierCommerce() {
        ArrayList<CarteQuartier> quartiers = new ArrayList<>();
        quartiers.add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        quartiers.add(new CarteQuartier(5.1, "Taverne", "Commerce et Artisanat", 3));
        quartiers.add(new CarteQuartier(3.2, "Tour de guet", "Soldatesque", 1));
        Batisseur pouvoir = Mockito.mock(Batisseur.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(batisseur, quartiers);
        CarteQuartier carteCommerce = pouvoir.choixDeQuartier(batisseur, quartiers);
        assertEquals("Commerce et Artisanat", carteCommerce.getGemme());
    }

    @Test
    void choixQuartierNoblesse() {
        ArrayList<CarteQuartier> quartiers = new ArrayList<>();
        quartiers.add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        quartiers.add(new CarteQuartier(5.1, "Manoir", "Noblesse", 3));
        quartiers.add(new CarteQuartier(3.2, "Tour de guet", "Soldatesque", 1));
        Batisseur pouvoir = Mockito.mock(Batisseur.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(batisseur, quartiers);
        CarteQuartier carteNoblesse = pouvoir.choixDeQuartier(batisseur, quartiers);
        assertEquals("Noblesse", carteNoblesse.getGemme());
    }

    @Test
    void choixParDefaut() {
        ArrayList<CarteQuartier> quartiers = new ArrayList<>();
        quartiers.add(new CarteQuartier(5.1, "Tour de guet", "Soldatesque", 3));
        quartiers.add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        Batisseur pouvoir = Mockito.mock(Batisseur.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeQuartier(batisseur, quartiers);
        CarteQuartier carte = pouvoir.choixDeQuartier(batisseur, quartiers);
        assertNotEquals("Noblesse", carte.getGemme());
        assertNotEquals("Commerce et Artisanat", carte.getGemme());
        assertEquals("Religion", carte.getGemme());
    }

    @Test
    void choixMaxPoint() {
        joueurs.get(0).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 3));
        joueurs.get(2).getQuartiersConstruits().add(new CarteQuartier(3.1, "Temple", "Religion", 1));
        Batisseur pouvoir = Mockito.mock(Batisseur.class);
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleJoueur(batisseur, joueurs);
        Joueur joueurCible = pouvoir.choixDeCibleJoueur(batisseur, joueurs);
        assertEquals(joueurs.get(0), joueurCible);
    }

    @Test
    void choixCibleArchitecte() {
        batisseur.setPersonnage(new CartePersonnage(1, "Voleur", null));
        Batisseur pouvoir = Mockito.mock(Batisseur.class);
        MoteurDeJeu.deck.getPersonnages().removeIf(cartePersonnage -> cartePersonnage.getNom().equals("Marchand"));
        Mockito.doCallRealMethod().when(pouvoir).choixDeCibleCartePersonnage(batisseur, MoteurDeJeu.deck.getPersonnages());
        CartePersonnage personnageCible = pouvoir.choixDeCibleCartePersonnage(batisseur, MoteurDeJeu.deck.getPersonnages());
        assertEquals("Architecte", personnageCible.getNom());
    }
}
