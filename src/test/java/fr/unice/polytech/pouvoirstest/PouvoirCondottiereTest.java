package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.*;
import fr.unice.polytech.pouvoirs.PouvoirCondottiere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PouvoirCondottiereTest {
    Joueur condottiere;
    Joueur marchand;
    Joueur eveque;
    Joueur joueurAyantFini;
    MoteurDeJeu moteurDeJeu;
    Strategie strategie;
    ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<CarteQuartier> quartiersc1 = new ArrayList<>();
    CarteQuartier quartierc1;

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs);

        condottiere = joueurs.get(0);
        condottiere.setPersonnage(new CartePersonnage(8, "Condottiere", "Soldatesque", "Le Condottiere peut détruire un quartier de son choix en payant à la banque le coût de construction du quartier moins un. Chaque quartier militaire qu'il possède lui rapporte une pièce d'or."));

        marchand = joueurs.get(1);
        marchand.setPersonnage(new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or."));

        eveque = joueurs.get(2);
        eveque.setPersonnage(new CartePersonnage(5, "Evêque", "Religion", "L'Évêque ne peut pas être attaqué par le Condottière. Chaque quartier religieux qu'il possède lui rapporte une pièce d'or."));

        joueurAyantFini = joueurs.get(3);
        joueurAyantFini.setPersonnage(new CartePersonnage(7, "Architecte", null, "L'Architecte pioche deux cartes quartier en plus. il peut bâtir jusqu'à trois quartiers."));

        quartierc1 = new CarteQuartier(14.2, "Tour de guet", "Soldatesque", 1);
        quartiersc1.add(quartierc1);
        moteurDeJeu.setJoueurs(joueurs);
    }

    @Test
    void taxesAjoutTest() {
        condottiere.setQuartiers(quartiersc1);
        assertEquals(2, condottiere.getOr());
        condottiere.construireQuartier();
        assertEquals(1, condottiere.getOr());
        strategie = new Strategie(condottiere);
        strategie.actionPersonnage();
        assertEquals(1, condottiere.getOr());
    }

    @Test
    void pouvoirTest() {
        PouvoirCondottiere pouvoir = Mockito.mock(PouvoirCondottiere.class);
        Mockito.doCallRealMethod().when(pouvoir).utiliserPouvoir(condottiere);
        Mockito.doCallRealMethod().when(pouvoir).hasEnoughMoney(condottiere, quartierc1);
        Mockito.doCallRealMethod().when(pouvoir).choixQuartierAleatoire(condottiere, marchand);
        Mockito.when(pouvoir.cibleAleatoire(condottiere)).thenReturn(marchand);
        marchand.setOr(50);
        marchand.setQuartiers(quartiersc1);
        marchand.construireQuartier();
        condottiere.setOr(50);
        pouvoir.utiliserPouvoir(condottiere);
        assertEquals(0, marchand.getQuartiersConstruits().size());
    }

}