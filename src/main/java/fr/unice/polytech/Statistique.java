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

    public void ajoutGagnant(Joueur joueur) {
        if (this.statistiqueVictoireData.containsKey(joueur.getNom())) {
            int nombreVictoire = this.statistiqueVictoireData.get(joueur.getNom());
            this.statistiqueVictoireData.replace(joueur.getNom(), ++nombreVictoire);
        } else {
            this.statistiqueVictoireData.put(joueur.getNom(), 1);
        }
    }

    public void ajoutScore(ArrayList<Joueur> joueurs) {
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

        String[][] data = csvReader.getData();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        ecritureCsv.clearCsv();

        for (Joueur joueur : MoteurDeJeu.joueurs) {
            if (!this.statistiqueVictoireData.containsKey(joueur.getNom())) {
                this.statistiqueVictoireData.put(joueur.getNom(), 0);
            }
        }

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

    private int trouverLigne(String[][] tableau, String nom) {
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i][0].equals(nom)) {
                return i;
            }
        }
        return tableau.length;
    }

    @Override
    public String toString() {
        return "nombreVictoire=" + statistiqueVictoireData + '}';
    }
}
