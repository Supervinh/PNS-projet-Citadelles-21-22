package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvEcriture;
import fr.unice.polytech.lecteurFichiers.CsvReader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Statistique {
    private final HashMap<String, Integer> statistiqueVictoireData = new HashMap<>();
    private final HashMap<String, Double> statistiqueScoreData = new HashMap<>();
    private final String[] titre = new String[]{"Nom", "Victoires", "Défaites", "Parties", "Ratio", "Score Moyen"};
    private String[][] data;
    private final int marge = 4;

    public void ajoutStats(MoteurDeJeu moteurDeJeu) {
        this.ajoutGagnant(moteurDeJeu.obtenirGagnant(MoteurDeJeu.joueurs));
        this.ajoutScore(MoteurDeJeu.joueurs);
    }

    private void ajoutGagnant(Joueur joueur) {
        if (this.statistiqueVictoireData.containsKey(joueur.getNom())) {
            int nombreVictoire = this.statistiqueVictoireData.get(joueur.getNom());
            this.statistiqueVictoireData.replace(joueur.getNom(), ++nombreVictoire);
        } else {
            this.statistiqueVictoireData.put(joueur.getNom(), 1);
        }
    }

    private void ajoutScore(ArrayList<Joueur> joueurs) {
        for (Joueur joueur : joueurs) {
            if (this.statistiqueScoreData.containsKey(joueur.getNom())) {
                double scoreMoy = this.statistiqueScoreData.get(joueur.getNom());
                this.statistiqueScoreData.replace(joueur.getNom(), scoreMoy + (joueur.getPoints() / (double) Main.nombrePartie));
            } else {
                this.statistiqueScoreData.put(joueur.getNom(), joueur.getPoints() / (double) Main.nombrePartie);
            }
        }
    }

    public void ajoutAuxCSV() {
        CsvReader csvReader = new CsvReader();
        CsvEcriture ecritureCsv = new CsvEcriture();

        csvReader.lireStatistiques();
        this.data = csvReader.getData();
        ecritureCsv.clearCsv();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        this.rajouteNonGagnant();

        for (Map.Entry<String, Integer> entry : this.statistiqueVictoireData.entrySet()) {
            String nom = entry.getKey();
            int victoireTotal = entry.getValue() + Integer.parseInt(data[trouverLigne(data, nom)][1]);
            int partieTotal = Main.nombrePartie + Integer.parseInt(data[trouverLigne(data, nom)][3]);
            int defaiteTotal = partieTotal - victoireTotal;
            String ratio = df.format(victoireTotal / (double) (partieTotal));

            double moyenCSV = Double.parseDouble(data[trouverLigne(data, nom)][5]);
            String scoreMoyenTotal = df.format(0.5 * (this.statistiqueScoreData.get(nom) + (moyenCSV <= 0 ? this.statistiqueScoreData.get(nom) : moyenCSV)));

            ecritureCsv.ecrireStatistiques(nom, victoireTotal, defaiteTotal, partieTotal, ratio, scoreMoyenTotal);
        }
    }

    private void rajouteNonGagnant() {
        for (Joueur joueur : MoteurDeJeu.joueurs) {
            if (!this.statistiqueVictoireData.containsKey(joueur.getNom())) {
                this.statistiqueVictoireData.put(joueur.getNom(), 0);
            }
        }
    }

    private int trouverLigne(String[][] tableau, String nom) {
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i][0].equals(nom)) {
                return i;
            }
        }
        return tableau.length;
    }

    public void printStatTableau() {
        CsvReader csvReader = new CsvReader();
        this.data = csvReader.getData();
        Object[] titre = this.titre;
        System.out.format("\n%" + (this.largeurColonne(0) - this.marge) + "s%" + this.largeurColonne(1) + "s%" + this.largeurColonne(2) + "s%" + this.largeurColonne(3) + "s%" + this.largeurColonne(4) + "s%" + this.largeurColonne(5) + "s%n", titre);
        for (int i = 0; i < this.titre.length; i++) {
            System.out.print("─".repeat(i == 0 ? this.largeurColonne(i) - this.marge : this.largeurColonne(i)));
        }
        System.out.println();
        for (final Object[] row : this.data) {
            System.out.format("%" + (this.largeurColonne(0) - this.marge) + "s%" + this.largeurColonne(1) + "s%" + this.largeurColonne(2) + "s%" + this.largeurColonne(3) + "s%" + this.largeurColonne(4) + "s%" + this.largeurColonne(5) + "s%n", row);
        }
    }

    private int largeurColonne(int numColonne) {
        int largeur = 0;
        for (String[] ligne : this.data) {
            largeur = Math.max(largeur, ligne[numColonne].length());
        }
        largeur = Math.max(largeur, this.titre[numColonne].length());
        return largeur + this.marge;
    }

    @Override
    public String toString() {
        return "nombreVictoire=" + statistiqueVictoireData + '}';
    }
}
