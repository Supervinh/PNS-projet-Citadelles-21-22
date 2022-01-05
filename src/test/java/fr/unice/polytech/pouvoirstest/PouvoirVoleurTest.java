package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.pouvoirs.PouvoirVoleur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PouvoirVoleurTest {
    MoteurDeJeu moteurDeJeu = new MoteurDeJeu();
    ArrayList<Joueur> joueurs = new ArrayList<>();
    Joueur voleur;
    Joueur marchand;
    CartePersonnage personnage;

    @BeforeEach
    void setUp() {
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        personnage = new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or.");
        voleur = joueurs.get(0);
        marchand = joueurs.get(1);
        voleur.setPersonnage(new CartePersonnage(2, "Voleur", "None", "Le Voleur peut voler le trésor du personnage de son choix. Il ne peut voler ni l'Assassin, ni un personnage assassiné. Le vol prendra effet au début du tour du personnage volé."));
        marchand.setPersonnage(personnage);
        moteurDeJeu.setJoueurs(joueurs);
    }

    void specialSetUp() {
        marchand.setPersonnage(new CartePersonnage(3, "Assassin", "None"));
        joueurs.remove(voleur);
        joueurs.remove(marchand);
        System.out.println(joueurs.size());
        for (Joueur joueur : joueurs) {
            joueur.piocherPersonnage();
            while (joueur.getPersonnage().getNom().equals("Marchand") || joueur.getPersonnage().getNom().equals("Voleur"))
                joueur.piocherPersonnage();
        }
        joueurs.add(voleur);
        joueurs.add(marchand);
    }

    @RepeatedTest(100)
    void aVole() {
        PouvoirVoleur pouvoir = Mockito.mock(PouvoirVoleur.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(voleur);
        Mockito.when(pouvoir.cibleAleatoire(voleur)).thenReturn(personnage);
        pouvoir.utiliserPouvoir(voleur);
        assertEquals(4, voleur.getOr());
    }

    @RepeatedTest(100)
    void neVolePas() {
        PouvoirVoleur pouvoir = Mockito.mock(PouvoirVoleur.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(voleur);
        Mockito.when(pouvoir.cibleAleatoire(voleur)).thenReturn(personnage);
        specialSetUp();
        pouvoir.utiliserPouvoir(voleur);
        assertEquals(2, voleur.getOr());
    }

    @RepeatedTest(100)
    void estMort() {
        PouvoirVoleur pouvoir = Mockito.mock(PouvoirVoleur.class);
        Mockito.doCallRealMethod().when(pouvoir).estPersonnageMort(personnage);
        marchand.setMort(true);
        assertTrue(pouvoir.estPersonnageMort(personnage));
    }
}
