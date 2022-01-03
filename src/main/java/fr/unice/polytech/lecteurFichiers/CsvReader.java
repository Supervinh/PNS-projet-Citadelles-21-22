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
     * Le nom du joueur.
     */
    private String nom;

    /**
     * Le nombre de victoires du joueur.
     */
    private int victoires;

    /**
     * Le nombre de defaites du joueur.
     */
    private int defaites;

    /**
     * Le nombre de parties total que le joueur a joué.
     */
    private int parties;

    /**
     * Le ratio de victoire du joueur.
     */
    private double ratio;

    private String[][] listeStatistiques;

    /**
     * Permet de lire le fichier csv.
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
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                //Verifying the read data here
                System.out.println(Arrays.toString(nextLine));
                nom = nextLine[0];
                victoires = Integer.parseInt(nextLine[1]);
                defaites = Integer.parseInt(nextLine[2]);
                parties = Integer.parseInt(nextLine[3]);
                ratio = Double.parseDouble(nextLine[4]);
            }
        }
    }

    public String getNom() {
        return nom;
    }

    public int getVictoires() {
        return victoires;
    }

    public int getDefaites() {
        return defaites;
    }

    public int getParties() {
        return parties;
    }

    public double getRatio() {
        return ratio;
    }

    public String[][] getListeStatistiques() {
        return listeStatistiques;
    }

    

    public static void main(String[] args) throws Exception {
        CsvReader lire = new CsvReader();
        lire.lireStatistiques();
    }
}
