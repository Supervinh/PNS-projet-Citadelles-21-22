package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MoteurDeJeu {

    public static Deck deck;
    public static int nombre2Joueur = 5;
    public static int or2Depart = 2;
    public static int orAPiocher = 2;
    public static int carte2Depart = 4;
    public static int carteAPiocher = 1;
    public static int nombre2QuartiersAConstruire = 8;
    public static CouleurConsole cc = new CouleurConsole();
    public static ArrayList<Joueur> joueurs;
    private int nb2Tours = 0;
    private int roiIndex = 0;
    private boolean avaitRoi = true;

    public MoteurDeJeu() {
        deck = new Deck();
        joueurs = new ArrayList<>();
        System.out.println(this.hello());
        System.out.println("\n" + deck);

        this.initialiseJoueur();
        int nommbre2Personnages = deck.getPersonnages().size();
        while (this.pasFini()) {
            System.out.println("\n" + cc.seperateur2() + CouleurConsole.RESET + "Tour " + ++this.nb2Tours + cc.seperateur2());

            AtomicInteger k = new AtomicInteger(0);
            if (joueurs.stream().peek(v -> k.getAndIncrement()).anyMatch(Joueur::isEstRoi) && this.avaitRoi) {
                this.roiIndex = k.get() - 1;
                joueurs.get(this.roiIndex).setEstRoi(true);
            } else {
                joueurs.get(this.roiIndex).setEstRoi(false);
                joueurs.get((this.roiIndex + 1) % nombre2Joueur).setEstRoi(true);
            }

            for (int i = this.roiIndex; i < joueurs.size(); i++) {
                joueurs.get(i).piocherPersonnage();
            }
            for (int i = 0; i < this.roiIndex; i++) {
                joueurs.get(i).piocherPersonnage();
            }

            for (int i = 1; i <= nommbre2Personnages; i++) {
                for (Joueur joueur : joueurs) {
                    if (joueur.getPersonnage().getId() == i) this.tour2Jeu(joueur);
                }
            }
            System.out.println("\n" + joueurs);
            this.avaitRoi = joueurs.stream().anyMatch(joueur -> joueur.getPersonnage().getNom().equals("Roi") && !joueur.isEstTue());
            joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));
        }
        this.obtenirGagnant();
    }

    public static void pause(int x) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(x);
    }

    public String hello() {
        return cc.seperateur1() + "Citadelle Grp.H - Jeux entre Bots" + cc.seperateur1();
    }

    public void initialiseJoueur() {
        System.out.println("\n" + cc.seperateur1() + CouleurConsole.RESET + "Entrez Nom des Joueurs" + cc.seperateur1());
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            System.out.println(cc.tire() + "Joueur " + i + ": " + CouleurConsole.CYAN_BOLD + "CPU" + i + CouleurConsole.RESET);
            joueurs.add(new Joueur(CouleurConsole.CYAN_BOLD + "CPU" + i + CouleurConsole.RESET));
        }
        joueurs.get(0).setEstRoi(true);
    }

    public boolean pasFini() {
        return (joueurs.stream().anyMatch(joueur -> joueur.getQuartiersConstruits().size() < MoteurDeJeu.nombre2QuartiersAConstruire));
    }

    public void tour2Jeu(Joueur joueur) {
        System.out.println("\n" + cc.seperateur1() + CouleurConsole.RESET + "Tour de " + joueur.getNom() + cc.seperateur1());
        joueur.jouer();
    }

    public void obtenirGagnant() {
        joueurs.forEach(Joueur::calculePoints);
        int maxScore = joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
        ArrayList<Joueur> winners = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == maxScore).toList());
        switch (winners.size()) {
            case 1 -> System.out.print("\nLe " + CouleurConsole.RED + "Gagnant" + CouleurConsole.RESET + " est ");
            case 0 -> System.out.println("\nPas de " + CouleurConsole.RED + "Gagnant" + CouleurConsole.RESET);
            default -> System.out.println("\nLes " + CouleurConsole.RED + "Gagnants" + CouleurConsole.RESET + " sont: ");
        }
        winners.forEach(winner -> System.out.println(winner.getNom() + " avec " + CouleurConsole.YELLOW_BRIGHT + winner.getPoints() + CouleurConsole.RESET + " points"));
        this.montrerClassement();
    }

    public void montrerClassement() {
        Collections.sort(joueurs);
        System.out.println("\n" + cc.seperateur2() + CouleurConsole.CYAN_BRIGHT + "Classement apres " + this.nb2Tours + " Tours" + cc.seperateur2());
        joueurs.forEach(joueur -> System.out.println(cc.tire() + joueur.getNom() + " a " + CouleurConsole.YELLOW_BRIGHT + joueur.getPoints() + CouleurConsole.RESET + " points"));
    }
}
