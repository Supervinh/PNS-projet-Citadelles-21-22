package fr.unice.polytech;

import java.util.logging.Level;

/**
 * Classe main permettant de lancer le jeu
 */
public class Main {
    public static int nombrePartie = 1000;

    public static void main(String[] args) {
        Affichage.citadelle();
        MoteurDeJeu mj;
        MoteurDeJeu.nbJoueurs = 7;

        MoteurDeJeu.setMessageLvl(Level.ALL);
        Statistique statistique = new Statistique("Meilleure Bot contre les autres");
        long startTime = System.nanoTime();
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.initialiseJoueurs(MoteurDeJeu.joueurs, !MoteurDeJeu.nomAleatoire);
            MoteurDeJeu.joueurs.get(0).getStrategie().setStrategie("VStrat");
            MoteurDeJeu.joueurs.get(1).getStrategie().setStrategie("Rusher");
            MoteurDeJeu.joueurs.get(2).getStrategie().setStrategie("Commerce");
            MoteurDeJeu.joueurs.get(3).getStrategie().setStrategie("Opportuniste");
            MoteurDeJeu.joueurs.get(4).getStrategie().setStrategie("Merveille");
            MoteurDeJeu.joueurs.get(5).getStrategie().setStrategie("Batisseur");
            MoteurDeJeu.joueurs.get(6).getStrategie().setStrategie("Agressif");
            mj.printJoueursInitialises(MoteurDeJeu.joueurs);
            mj.lancerTourDeJeu(MoteurDeJeu.joueurs);
            mj.printGagnant(MoteurDeJeu.joueurs);
            mj.printClassement(MoteurDeJeu.joueurs);
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);

        MoteurDeJeu.setMessageLvl(Level.INFO);
        statistique = new Statistique("Meilleure Bot contre lui-même");
        startTime = System.nanoTime();
        for (int i = 0; i < nombrePartie; i++) {
            mj = new MoteurDeJeu();
            mj.initialiseJoueurs(MoteurDeJeu.joueurs, !MoteurDeJeu.nomAleatoire);
            MoteurDeJeu.joueurs.forEach(joueur -> joueur.getStrategie().setStrategie("VStrat"));
            mj.printJoueursInitialises(MoteurDeJeu.joueurs);
            mj.lancerTourDeJeu(MoteurDeJeu.joueurs);
            statistique.ajoutStats(mj);
        }
        statistique.ajoutAuxCSV();
        statistique.printStatTableau();
        Affichage.chrono(startTime);
    }
}