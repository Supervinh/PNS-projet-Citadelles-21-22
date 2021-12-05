package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.Strategie;
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
        //MockitoAnnotations.initMocks(this);
        moteurDeJeu.initialiseJoueurs(joueurs);
        personnage = new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or.");
        moteurDeJeu.piocherPersonnage(joueurs);
        assassin = joueurs.stream()
                .filter(joueur -> joueur.getPersonnage().getNom().equals("Assassin"))
                .findAny().orElse(joueurs.get(0));
        while (!assassin.getPersonnage().getNom().equals("Assassin")) {
            assassin.piocherPersonnage();
            if (!assassin.getPersonnage().getNom().equals("Assassin"))
                MoteurDeJeu.deck.ajoutePersonnage(assassin.getPersonnage());
        }
        joueurs.remove(assassin);
        marchand = joueurs.stream()
                .filter(joueur -> joueur.getPersonnage().getNom().equals("Marchand"))
                .findAny().orElse(joueurs.get(0));
        while (!marchand.getPersonnage().getNom().equals("Marchand")) {
            marchand.piocherPersonnage();
            if (!marchand.getPersonnage().getNom().equals("Marchand"))
                MoteurDeJeu.deck.ajoutePersonnage(marchand.getPersonnage());
        }
        joueurs.add(assassin);
        moteurDeJeu.setJoueurs(joueurs);
    }

    @Test
    void estTue() {
        PouvoirAssassin pouvoir = Mockito.mock(PouvoirAssassin.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(assassin);
        Mockito.doCallRealMethod().when(pouvoir).tue(marchand);
        Mockito.when(pouvoir.cibleAleatoire(assassin)).thenReturn(personnage);
        pouvoir.utiliserPouvoir(assassin);
        assertTrue(marchand.isEstTue());
    }

}
