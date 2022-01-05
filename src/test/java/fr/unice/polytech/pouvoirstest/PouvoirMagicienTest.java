package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.pouvoirs.PouvoirMagicien;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;


class PouvoirMagicienTest {
    MoteurDeJeu moteurDeJeu = new MoteurDeJeu();
    ArrayList<Joueur> joueurs = new ArrayList<>();
    Joueur magicien;
    Joueur marchand;
    CartePersonnage personnage;
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        personnage = new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or.");
        magicien = joueurs.get(0);
        marchand = joueurs.get(1);
        magicien.setPersonnage(new CartePersonnage(3, "Magicien", "None", "Au choix: Le ¨Magicien peut échanger la totalité de ses cartes avec le joueur de son choix. Ou: le Magicien peut échanger des cartes de sa main contre le même nombre de cartes de la pioche."));
        marchand.setPersonnage(personnage);
        joueurs.remove(magicien);
        joueurs.remove(marchand);
        for (Joueur joueur : joueurs) {
            joueur.piocherPersonnage();
            if (joueur.getPersonnage().getNom().equals("Magicien") || joueur.getPersonnage().getNom().equals("Marchand"))
                joueur.piocherPersonnage();
        }
        joueurs.add(marchand);
        joueurs.add(magicien);
        moteurDeJeu.setJoueurs(joueurs);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void aEchangeSesCartesAvecJoueur() {
        PouvoirMagicien pouvoir = Mockito.mock(PouvoirMagicien.class);
        Mockito.doCallRealMethod().when(pouvoir).echangerCartesAvecJoueur(magicien);
        Mockito.when(pouvoir.cibleAleatoire(magicien)).thenReturn(marchand);
        magicien.setQuartiers(quartiers);
        pouvoir.echangerCartesAvecJoueur(magicien);
        assertEquals(4, magicien.getQuartiers().size());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void aEchangeSesCartesAvecLaPioche() {
        ArrayList<CarteQuartier> q = new ArrayList<>(magicien.getQuartiers());
        PouvoirMagicien pouvoir = Mockito.mock(PouvoirMagicien.class);
        Mockito.doCallRealMethod().when(pouvoir).echangerCartesAvecPioche(magicien, 3);
        pouvoir.echangerCartesAvecPioche(magicien, 3);
        assertEquals(4, magicien.getQuartiers().size());
        assertNotEquals(q, magicien.getQuartiers());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void echangePasAvecPioche() {
        PouvoirMagicien pouvoir = Mockito.mock(PouvoirMagicien.class);
        Mockito.doCallRealMethod().when(pouvoir).echangerCartesAvecPioche(magicien, 3);
        ArrayList<CarteQuartier> q = new ArrayList<>(magicien.getQuartiers());
        pouvoir.echangerCartesAvecPioche(magicien, -5);
        assertEquals(q, magicien.getQuartiers());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void testRandom() {
        PouvoirMagicien pouvoir = Mockito.mock(PouvoirMagicien.class);
        Mockito.doCallRealMethod().when(pouvoir).choixNombreQuartiers(magicien);
        Mockito.doCallRealMethod().when(pouvoir).cibleAleatoire(magicien);
        int m = pouvoir.choixNombreQuartiers(magicien);
        Joueur cible = pouvoir.cibleAleatoire(magicien);
        assertTrue(m < 5 && m > -1);
        assertNotEquals(magicien, cible);
    }
}
