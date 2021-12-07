package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.pouvoirs.PouvoirMagicien;
import fr.unice.polytech.pouvoirs.PouvoirVoleur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PouvoirVoleurTest {
    MoteurDeJeu moteurDeJeu = new MoteurDeJeu();
    ArrayList<Joueur> joueurs = new ArrayList<>();
    Joueur voleur;
    Joueur marchand;
    CartePersonnage personnage;


    @BeforeEach
    void setUp() {
        moteurDeJeu.initialiseJoueurs(joueurs);
        personnage = new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or.");
        voleur = joueurs.get(0);
        marchand = joueurs.get(1);
        voleur.setPersonnage(new CartePersonnage(2, "Voleur", null, "Le Voleur peut voler le trésor du personnage de son choix. Il ne peut voler ni l'Assassin, ni un personnage assassiné. Le vol prendra effet au début du tour du personnage volé."));
        marchand.setPersonnage(personnage);
        moteurDeJeu.setJoueurs(joueurs);
    }

    @Test
    void aVole() {
        PouvoirVoleur pouvoir = Mockito.mock(PouvoirVoleur.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(voleur);
        Mockito.when(pouvoir.cibleAleatoire(voleur)).thenReturn(personnage);
        pouvoir.utiliserPouvoir(voleur);
        assertEquals(4, voleur.getOr());
    }

}
