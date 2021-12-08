package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.pouvoirs.PouvoirMagicien;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PouvoirMagicienTest {
    MoteurDeJeu moteurDeJeu = new MoteurDeJeu();
    ArrayList<Joueur> joueurs = new ArrayList<>();
    Joueur magicien;
    Joueur marchand;
    CartePersonnage personnage;
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();


    @BeforeEach
    void setUp() {
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        personnage = new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or.");
        magicien = joueurs.get(0);
        marchand = joueurs.get(1);
        magicien.setPersonnage(new CartePersonnage(3, "Magicien", "None", "Au choix: Le ¨Magicien peut échanger la totalité de ses cartes avec le joueur de son choix. Ou: le Magicien peut échanger des cartes de sa main contre le même nombre de cartes de la pioche."));
        marchand.setPersonnage(personnage);
        moteurDeJeu.setJoueurs(joueurs);
    }

    @Test
    void aEchangeSesCartesAvecJoueur() {
        PouvoirMagicien pouvoir = Mockito.mock(PouvoirMagicien.class);
        Mockito.doCallRealMethod().when(pouvoir).echangerCartesAvecJoueur(magicien);
        Mockito.when(pouvoir.cibleAleatoire(magicien)).thenReturn(personnage);
        magicien.setQuartiers(quartiers);
        pouvoir.echangerCartesAvecJoueur(magicien);
        assertEquals(4, magicien.getQuartiers().size());
    }

    @Test
    void aEchangeSesCartesAvecLaPioche() {
        PouvoirMagicien pouvoir = Mockito.mock(PouvoirMagicien.class);
        Mockito.doCallRealMethod().when(pouvoir).echangerCartesAvecPioche(magicien, 3);
        pouvoir.echangerCartesAvecPioche(magicien, 3);
        assertEquals(4, magicien.getQuartiers().size());
    }

}
