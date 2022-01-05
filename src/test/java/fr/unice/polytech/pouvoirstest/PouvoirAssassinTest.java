package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.pouvoirs.PouvoirAssassin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class PouvoirAssassinTest {
    MoteurDeJeu moteurDeJeu = new MoteurDeJeu();
    ArrayList<Joueur> joueurs = new ArrayList<>();
    Joueur assassin;
    Joueur marchand;
    CartePersonnage personnage;


    @BeforeEach
    void setUp() {
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        personnage = new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or.");
        assassin = joueurs.get(0);
        marchand = joueurs.get(1);
        assassin.setPersonnage(new CartePersonnage(1, "Assassin", "None", "L'Assassin peut tuer le personnage de son choix. Celui-ci ne pourra pas jouer ce tour-ci"));
        marchand.setPersonnage(personnage);
        moteurDeJeu.setJoueurs(joueurs);
    }

    @RepeatedTest(100)
    void estTue() {
        PouvoirAssassin pouvoir = Mockito.mock(PouvoirAssassin.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(assassin);
        Mockito.doCallRealMethod().when(pouvoir).tue(marchand);
        Mockito.doCallRealMethod().when(pouvoir).cibleExistante(personnage);
        Mockito.when(pouvoir.cibleAleatoire(assassin)).thenReturn(personnage);
        pouvoir.utiliserPouvoir(assassin);
        assertTrue(marchand.isMort());
    }

    @RepeatedTest(100)
    void pas2Cible() {
        CartePersonnage voleur = new CartePersonnage(2, "Voleur", "None");
        PouvoirAssassin pouvoir = Mockito.mock(PouvoirAssassin.class);
        Mockito.doCallRealMethod().when(pouvoir).cibleExistante(voleur);
        joueurs.remove(assassin);
        joueurs.remove(marchand);
        for (Joueur joueur : joueurs) {
            do {
                joueur.piocherPersonnage();
            }
            while (joueur.getPersonnage().getNom().equals("Voleur"));
        }

        assertNull(pouvoir.cibleExistante(voleur));
    }

    @RepeatedTest(100)
    void testRandom() {
        PouvoirAssassin pouvoir = Mockito.mock(PouvoirAssassin.class);
        Mockito.doCallRealMethod().when(pouvoir).cibleAleatoire(assassin);
        CartePersonnage cible = pouvoir.cibleAleatoire(assassin);
        assertNotEquals(assassin.getPersonnage(), cible);
    }

}
