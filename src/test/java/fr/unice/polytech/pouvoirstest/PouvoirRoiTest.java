package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.*;
import fr.unice.polytech.pouvoirs.PouvoirEveque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

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
    }

    @Test
    void taxesAjoutTest() {
        PouvoirEveque taxe = Mockito.mock(PouvoirEveque.class);
        Mockito.doCallRealMethod().when(taxe).utiliserPouvoir(roi);
        Mockito.doCallRealMethod().when(taxe).recupererTaxes(roi);
        roi.setQuartiers(quartiers);
        roi.ajouteOr(1);
        roi.construireQuartier();
        taxe.utiliserPouvoir(roi);
        assertEquals(1, roi.getOr());
    }

    @Test
    void pasDeTaxe() {
        PouvoirEveque pasTaxe = Mockito.mock(PouvoirEveque.class);
        Mockito.doCallRealMethod().when(pasTaxe).utiliserPouvoir(roi);
        Mockito.doCallRealMethod().when(pasTaxe).recupererTaxes(roi);
        pasTaxe.utiliserPouvoir(roi);
        assertEquals(2, roi.getOr());
    }

}
