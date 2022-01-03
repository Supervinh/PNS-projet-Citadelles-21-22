package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;

/**
 * Permet d'écrire dans un fichier csv.
 */
public class CsvEcriture {

    public static void main(String[] args) throws Exception {
        CsvEcriture ecrire = new CsvEcriture();
        CsvReader lire = new CsvReader();

        ecrire.ecrireStatistiques("Bob", 2, 2, 4, "0.5");
    }

    /**
     * Permet d'écrire dans le fichier csv.
     *
     * @param Nom       Le nom du joueur.
     * @param Victoires Le nombre de victoires du joueur.
     * @param Defaites  Le nombre de défaites du joueur.
     * @param Parties   Le nombre de partie total joué.
     * @param Ratio     Le ratio de victoire du joueur.
     */
    public void ecrireStatistiques(String Nom, int Victoires, int Defaites, int Parties, String Ratio) throws Exception {
        String csv = "src/main/resources/save/results.csv";

        CSVWriter writer = new CSVWriter(new FileWriter(csv, true), ',', CSVWriter.NO_QUOTE_CHARACTER);

        String[] record = (Nom + "," + Victoires + "," + Defaites + "," + Parties + "," + Ratio).split(",");

        writer.writeNext(record);

        writer.close();
    }
}
