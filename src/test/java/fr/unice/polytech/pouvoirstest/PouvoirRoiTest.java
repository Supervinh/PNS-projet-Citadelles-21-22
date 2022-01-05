package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.pouvoirs.PouvoirRoi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;


public class PouvoirRoiTest {
    Joueur roi;
    MoteurDeJeu moteurDeJeu;
    ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;

    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        roi = joueurs.get(1);
        roi.setPersonnage(new CartePersonnage(4, "Roi", "Noblesse", "Le roi prend la carte Couronne et choisira en premier son personnage au prochain tour. Chaque quartier noble qu'il possède lui rapporte une pièce d'or."));
        quartier = new CarteQuartier(5, "Manoir", "Noblesse", 3);
        quartiers.add(quartier);
        MoteurDeJeu.setMessageLvl(Level.OFF);
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void taxesAjoutTest() {
        PouvoirRoi taxe = Mockito.mock(PouvoirRoi.class);
        Mockito.doCallRealMethod().when(taxe).utiliserPouvoir(roi);
        Mockito.doCallRealMethod().when(taxe).recupererTaxes(roi);
        roi.ajouteOr(-1 * roi.getOr());
        roi.setQuartiersConstruits(quartiers);
        assertFalse(roi.isRoi());
        taxe.utiliserPouvoir(roi);
        assertTrue(roi.isRoi());
        assertEquals(1, roi.getOr());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void pasDeTaxe() {
        PouvoirRoi pasTaxe = Mockito.mock(PouvoirRoi.class);
        Mockito.doCallRealMethod().when(pasTaxe).utiliserPouvoir(roi);
        Mockito.doCallRealMethod().when(pasTaxe).recupererTaxes(roi);
        pasTaxe.utiliserPouvoir(roi);
        assertEquals(2, roi.getOr());
    }
}
