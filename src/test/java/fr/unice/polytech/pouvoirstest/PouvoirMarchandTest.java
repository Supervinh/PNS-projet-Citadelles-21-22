package fr.unice.polytech.pouvoirstest;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.pouvoirs.PouvoirMarchand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PouvoirMarchandTest {
    Joueur marchand;
    ArrayList<CarteQuartier> quartiers = new ArrayList<>();
    CarteQuartier quartier;
    MoteurDeJeu moteurDeJeu;
    ArrayList<Joueur> joueurs = new ArrayList<>();


    @BeforeEach
    void setUp() {
        moteurDeJeu = new MoteurDeJeu();
        moteurDeJeu.initialiseJoueurs(joueurs, true);
        marchand = joueurs.get(0);
        marchand.setPersonnage(new CartePersonnage(6, "Marchand", "Commerce et Artisanat", "Le Marchand reçoit une pièce d'or en plus au début de son tour. Chaque quartier marchand qu'il possède lui rapporte une pièce d'or."));
        quartier = new CarteQuartier(11.2, "Comptoir", "Commerce et Artisanat", 3);
        quartiers.add(quartier);
    }

    @Test
    void taxesAjoutTest() {
        PouvoirMarchand taxe = Mockito.mock(PouvoirMarchand.class);
        Mockito.doCallRealMethod().when(taxe).utiliserPouvoir(marchand);
        Mockito.doCallRealMethod().when(taxe).recupererTaxes(marchand);
        marchand.setQuartiers(quartiers);
        marchand.ajouteOr(1);
        marchand.construireQuartier();
        taxe.utiliserPouvoir(marchand);
        assertEquals(2, marchand.getOr());
    }

    @Test
    void pasDeTaxe() {
        PouvoirMarchand pasTaxe = Mockito.mock(PouvoirMarchand.class);
        Mockito.doCallRealMethod().when(pasTaxe).utiliserPouvoir(marchand);
        Mockito.doCallRealMethod().when(pasTaxe).recupererTaxes(marchand);
        marchand.setOr(0);
        pasTaxe.utiliserPouvoir(marchand);
        assertEquals(1, marchand.getOr());
    }
}