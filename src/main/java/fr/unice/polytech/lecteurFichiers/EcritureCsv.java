package fr.unice.polytech.lecteurFichiers;

import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Permet d'écrire dans un fichier csv.
 */
public class EcritureCsv {

    /**
     * Permet d'écrire dans le fichier csv.
     *
     * @param Nom       Le nom du joueur.
     * @param Victoires Le nombre de victoires du joueur.
     * @param Defaites  Le nombre de défaites du joueur.
     * @param Parties   Le nombre de partie total joué.
     * @param Ratio     Le ratio de victoire du joueur.
     * @throws Exception
     */
    public void ecrireStatistiques(String Nom, int Victoires, int Defaites, int Parties, double Ratio) throws Exception {
        String csv = "src/main/resources/save/results.csv";

        CSVWriter writer = new CSVWriter(new FileWriter(csv, false), ',', CSVWriter.NO_QUOTE_CHARACTER);

        String[] record = (Nom + "," + Victoires + "," + Defaites + "," + Parties + "," + Ratio).split(",");

        writer.writeNext(record);

        writer.close();
    }


    public static void main(String[] args) throws Exception {
        EcritureCsv ecrire = new EcritureCsv();
        CsvReader lire = new CsvReader();

        ecrire.ecrireStatistiques("Bob", 2, 2, 4, 0.5);
    }
}
