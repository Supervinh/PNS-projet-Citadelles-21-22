package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;

/**
 * Permet de lire un fichier csv.
 */
public class CsvReader {

    /**
     * Permet de lire le csv.
     */
    private CSVReader reader;

    /**
     * On commence à lire à la ligne 0.
     */
    private int nombreLigne = 0;

    /**
     * Liste contenant les valeurs du fichier csv.
     */
    private String[][] data;

    /**
     * On instantie le tableau avec le nombre de ligne du fichier.
     */
    public CsvReader() {
        try {
            this.calculeLigne();
            this.data = new String[this.nombreLigne][5];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet de récupérer le tableau de valeurs du fichier csv.
     *
     * @return Le tableau de valeurs.
     */
    public String[][] getData() {
        lireStatistiques();
        return data;
    }

    /**
     * Permet de lire le fichier csv et d'initialiser le tableau avec les valeurs du fichier csv.
     */
    @SuppressWarnings("resource")
    public void lireStatistiques() {
        String[] nextLine;
        int lineNum = 0;
        try {
            this.reader = new CSVReader(new FileReader("src/main/resources/save/results.csv"), ';', ' ', 1);
            while ((nextLine = this.reader.readNext()) != null) {
                data[lineNum++] = nextLine;
            }
            this.reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Calcule le nombre de lignes dans le fichier.
     */
    public void calculeLigne() {
        try {
            this.reader = new CSVReader(new FileReader("src/main/resources/save/results.csv"), ';', ' ', 1);
            while ((this.reader.readNext()) != null) {
                this.nombreLigne++;
            }
            this.reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
