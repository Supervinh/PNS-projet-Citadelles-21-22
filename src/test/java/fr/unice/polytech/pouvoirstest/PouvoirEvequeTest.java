package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.pouvoirs.PouvoirEveque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PouvoirEvequeTest {
    final int ITERATIONS = 100;
    Joueur eveque;
    MoteurDeJeu moteurDeJeu;
    ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        eveque = joueurs.get(1);
        eveque.setPersonnage(new CartePersonnage(5, "Évêque", "Religion", "L'Évêque ne peut pas être attaqué par le Condottière. Chaque quartier religieux qu'il possède lui rapporte une pièce d'or."));
        quartier = new CarteQuartier(3, "Monastère", "Religion", 3);
        quartiers.add(quartier);
        MoteurDeJeu.setMessageLvl(Level.OFF);
    }

    @RepeatedTest(ITERATIONS)
    void taxesAjoutTest() {
        PouvoirEveque taxe = Mockito.mock(PouvoirEveque.class);
        Mockito.doCallRealMethod().when(taxe).utiliserPouvoir(eveque);
        Mockito.doCallRealMethod().when(taxe).recupererTaxes(eveque);
        eveque.ajouteOr(-1 * eveque.getOr());
        eveque.setQuartiersConstruits(quartiers);
        taxe.utiliserPouvoir(eveque);
        assertEquals(1, eveque.getOr());
    }

    @RepeatedTest(ITERATIONS)
    void pasTaxe() {
        PouvoirEveque taxe = Mockito.mock(PouvoirEveque.class);
        Mockito.doCallRealMethod().when(taxe).recupererTaxes(eveque);
        taxe.recupererTaxes(eveque);
        assertEquals(2, eveque.getOr());
    }

}
