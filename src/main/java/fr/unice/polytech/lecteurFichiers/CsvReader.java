package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVReader;
import fr.unice.polytech.MoteurDeJeu;

import java.io.FileReader;
import java.util.Arrays;

/**
 * Permet de lire un fichier csv.
 */
public class CsvReader {

    /**
     * Liste contenant les valeurs du fichier csv.
     */
    private String[][] data  = new String[MoteurDeJeu.nbJoueurs][5];

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
     *
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public void lireStatistiques() throws Exception {
        //Build reader instance
        //Read data.csv
        //Default seperator is comma
        //Default quote character is double quote
        //Start reading from line number 2 (line numbers start from zero)
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/save/results.csv"), ',', ' ', 1);

        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        int linenum = 0;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                //Verifying the read data here
                data[linenum++] = nextLine;
            }
        }
    }
}
