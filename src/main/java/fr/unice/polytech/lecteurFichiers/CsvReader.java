package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;

/**
 * Permet de lire un fichier csv.
 */
public class CsvReader {

    //Build reader instance
    //Read data.csv
    //Default separator is comma
    //Default quote character is double quote
    //Start reading from line number 2 (line numbers start from zero)
    private CSVReader reader;
    private int nombreLigne = 0;
    /**
     * Liste contenant les valeurs du fichier csv.
     */
    private String[][] data;

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
        return data;
    }

    /**
     * Permet de lire le fichier csv et d'initialiser le tableau avec les valeurs du fichier csv.
     */
    @SuppressWarnings("resource")
    public void lireStatistiques() {
        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        int lineNum = 0;
        try {
            this.reader = new CSVReader(new FileReader("src/main/resources/save/results.csv"), ',', ' ', 1);
            while ((nextLine = this.reader.readNext()) != null) {
                //Verifying the read data here
                data[lineNum++] = nextLine;
            }
            this.reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void calculeLigne() {
        try {
            this.reader = new CSVReader(new FileReader("src/main/resources/save/results.csv"), ',', ' ', 1);
            while ((this.reader.readNext()) != null) {
                this.nombreLigne++;
            }
            this.reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
