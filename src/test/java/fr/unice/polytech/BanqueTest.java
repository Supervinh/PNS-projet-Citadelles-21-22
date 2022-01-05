package fr.unice.polytech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BanqueTest {
    Banque banque;
    MoteurDeJeu moteurDeJeu;

    @BeforeEach
    void setup() {
        MoteurDeJeu.setMessageLvl(Level.OFF);
        this.banque = new Banque();
        this.moteurDeJeu = new MoteurDeJeu();
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void afficheBanqueDebut() {
        assertEquals("Banque{pieceEnJeu=" + this.banque.getPieceEnJeuColoured() + "}", this.banque.toString());
    }

    // À chaque tour vérifier que la somme d'argent en jeu est toujours bon.
    @RepeatedTest(MoteurDeJeu.iterationTest)
    void bonneCirculationOrPendantTouteLaPartie() {
        this.moteurDeJeu.jouer();
        assertTrue(MoteurDeJeu.banque.sommeArgentCirculationCorrecte());
    }

    @RepeatedTest(MoteurDeJeu.iterationTest)
    void bonneTransactions() {
        int somme = new Random().nextInt(35);
        if (somme > this.banque.getFonds()) {
            this.banque.transaction(somme);
            assertEquals(0, this.banque.getFonds());
        } else {
            int sommeInitiale = this.banque.getFonds();
            this.banque.transaction(somme);
            assertEquals(sommeInitiale - somme, this.banque.getFonds());
        }
    }
}