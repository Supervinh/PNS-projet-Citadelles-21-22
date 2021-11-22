package fr.unice.polytech;

import fr.unice.polytech.couleur.CouleurConsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class MoteurDeJeu {

    public static Deck deck;
    public static int nombre2Joueur = 5;
    public static int or2Depart = 2;
    public static int orAPiocher = 2;
    public static int carte2Depart = 4;
    public static int carteAPiocher = 1;
    public static int nombre2QuartiersAConstruire = 8;
    public static ArrayList<Joueur> joueurs;
    private int nb2Tours = 0;
    private int roiIndex = 0;
    private boolean avaitRoi = true;

    public MoteurDeJeu() {
        // Initialise le packet de carte ainsi que les Joueurs.
        deck = new Deck();
        joueurs = new ArrayList<>();
        System.out.println(this.hello());
        System.out.println(deck);
        this.initialiseJoueur();

        int nommbre2Personnages = deck.getPersonnages().size();
        while (joueurs.stream().noneMatch(Joueur::isFirst)) {
            System.out.println("\n" + CouleurConsole.seperateur2() + "Tour " + ++this.nb2Tours + CouleurConsole.seperateur2());

            // Trouver le Joueur 'estRoi=True' pour qu'il pioche en premier.
            AtomicInteger k = new AtomicInteger(0);
            this.roiIndex = joueurs.stream().peek(v -> k.getAndIncrement()).anyMatch(Joueur::isEstRoi) ? k.get() - 1 : this.roiIndex;
            if (!this.avaitRoi) {
                joueurs.get(this.roiIndex).setEstRoi(false);
                this.roiIndex = (this.roiIndex + 1) % nombre2Joueur;
                joueurs.get(this.roiIndex).setEstRoi(true);
            }

            // Joueur Pioche leur personnage dans l'ordre 'horaire' (ordre de la liste) commençant par le Joueur 'estRoi=True'.
            for (int i = this.roiIndex; i < joueurs.size(); i++) {
                joueurs.get(i).piocherPersonnage();
            }
            for (int i = 0; i < this.roiIndex; i++) {
                joueurs.get(i).piocherPersonnage();
            }

            // Joueur joue leur tour dans l'ordre des numéros des cartes personnage.
            for (int i = 1; i <= nommbre2Personnages; i++) {
                for (Joueur joueur : joueurs) {
                    if (joueur.getPersonnage().getId() == i) this.tour2Jeu(joueur);
                }
            }

            // Affichage de l'état du Joueur après le Tour. Cherche à savoir quelle method utiliser pour passer le 'estRoi=True'. Puis les Joueurs remettent leur carte dans le packet.
            System.out.println("\n" + joueurs);
            this.avaitRoi = joueurs.stream().anyMatch(joueur -> joueur.getPersonnage().getNom().equals("Roi") && !joueur.isEstTue());
            joueurs.forEach(joueur -> deck.ajoutePersonnage(joueur.getPersonnage()));
        }
        this.obtenirGagnant();
        this.montrerClassement();
    }

    public String hello() {
        return CouleurConsole.seperateur1() + "Citadelle Grp.H - Jeux entre Bots" + CouleurConsole.seperateur1() + "\n";
    }

    public void initialiseJoueur() {
        System.out.println("\n" + CouleurConsole.seperateur1() + "Entrez Nom des Joueurs" + CouleurConsole.seperateur1());
        for (int i = 1; i <= MoteurDeJeu.nombre2Joueur; i++) {
            System.out.println(CouleurConsole.tire() + "Joueur " + i + ": " + CouleurConsole.printCyan("CPU" + i));
            joueurs.add(new Joueur(CouleurConsole.printCyan("CPU" + i)));
        }
        joueurs.get(0).setEstRoi(true);
    }

    public void tour2Jeu(Joueur joueur) {
        System.out.println("\n" + CouleurConsole.seperateur1() + "Tour de " + joueur.getNom() + CouleurConsole.seperateur1());
        joueur.jouer();
        if (joueur.getQuartiersConstruits().size() >= MoteurDeJeu.nombre2QuartiersAConstruire && joueurs.stream().noneMatch(Joueur::isFirst)) {
            joueur.setFirst(true);
            System.out.println(joueur.getNom() + " a fini en " + CouleurConsole.printBlue("Premier"));
        }
    }

    public void obtenirGagnant() {
        joueurs.forEach(Joueur::calculePoints);
        int maxScore = joueurs.stream().mapToInt(Joueur::getPoints).max().orElse(0);
        ArrayList<Joueur> winners = new ArrayList<>(joueurs.stream().filter(joueur -> joueur.getPoints() == maxScore).toList());
        System.out.println();
        switch (winners.size()) {
            case 0 -> System.out.println("Pas de " + CouleurConsole.printRed("Gagnant"));
            case 1 -> System.out.print("Le " + CouleurConsole.printRed("Gagnant") + " est ");
            default -> System.out.println("Les " + CouleurConsole.printRed("Gagnants") + " sont: ");
        }
        winners.forEach(winner -> System.out.println(winner.getNom() + " avec " + CouleurConsole.printGold("" + winner.getPoints()) + " points"));
    }

    public void montrerClassement() {
        Collections.sort(joueurs);
        System.out.println("\n" + CouleurConsole.seperateur2() + CouleurConsole.printTurquoise("Classement apres " + this.nb2Tours + " Tours") + CouleurConsole.seperateur2());
        joueurs.forEach(joueur -> System.out.println(CouleurConsole.tire() + joueur.getNom() + " a " + CouleurConsole.printGold("" + joueur.getPoints()) + " points"));
    }
}
