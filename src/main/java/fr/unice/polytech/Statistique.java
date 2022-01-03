package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvReader;
import fr.unice.polytech.lecteurFichiers.CsvEcriture;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Statistique {
    private final HashMap<String, Integer> stats = new HashMap<>();
    private int nombrePartie = 0;

    public void ajoutGagnant(Joueur joueur) {
        try {
            int nombreVictoire = this.stats.get(joueur.getNom());
            this.stats.put(joueur.getNom(), ++nombreVictoire);

        } catch (Exception e) {
            this.stats.put(joueur.getNom(), 1);
        }
        this.nombrePartie++;
    }

    public void ajoutAuxCSVReader() {
        CsvReader csvReader = new CsvReader();
        CsvEcriture ecritureCsv = new CsvEcriture();
        try {
            csvReader.lireStatistiques();
        } catch (Exception e) {
            System.out.println(e.getMessage() + ", " + e);
            throw new RuntimeException();
        }
        String[][] data = csvReader.getData();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);


        for (Joueur joueur : MoteurDeJeu.joueurs) {
            if (!this.stats.containsKey(joueur.getNom())) {
                this.stats.put(joueur.getNom(), 0);
            }
        }

        for (Map.Entry<String, Integer> entry : this.stats.entrySet()) {
            String key = entry.getKey();
            int victoireTotal = entry.getValue() + Integer.parseInt(data[numLigne(data, key)][1]);
            int partieTotal = this.nombrePartie + Integer.parseInt(data[numLigne(data, key)][3]);
            int defaiteTotal = partieTotal - victoireTotal;
            String ratio = df.format(victoireTotal / (float) (partieTotal));
            try {
                ecritureCsv.ecrireStatistiques(key, victoireTotal, defaiteTotal, partieTotal, ratio);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    private int numLigne(String[][] tableau, String nom) {
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i][0].equals(nom)) {
                return i;
            }
        }
        return tableau.length;
    }

    @Override
    public String toString() {
        return "Victoires : " + stats;
    }
}
