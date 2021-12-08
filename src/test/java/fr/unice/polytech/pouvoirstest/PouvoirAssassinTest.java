package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.pouvoirs.PouvoirAssassin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @Test
    void estTue() {
        PouvoirAssassin pouvoir = Mockito.mock(PouvoirAssassin.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(assassin);
        Mockito.doCallRealMethod().when(pouvoir).tue(marchand);
        Mockito.when(pouvoir.cibleAleatoire(assassin)).thenReturn(personnage);
        pouvoir.utiliserPouvoir(assassin);
        assertTrue(marchand.isMort());
    }

}
