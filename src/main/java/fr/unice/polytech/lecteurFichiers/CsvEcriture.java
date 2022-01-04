package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;

/**
 * Permet d'écrire dans un fichier csv.
 */
public class CsvEcriture {

    /**
     * Permet d'écrire dans le fichier csv.
     *
     * @param Nom       Le nom du joueur.
     * @param Victoires Le nombre de victoires du joueur.
     * @param Defaites  Le nombre de défaites du joueur.
     * @param Parties   Le nombre de partie total joué.
     * @param Ratio     Le ratio de victoire du joueur.
     * @param Score     Le score moyen du joueur.
     */
    public void ecrireStatistiques(String Nom, int Victoires, int Defaites, int Parties, String Ratio, String Score) {
        try {
            String csv = "src/main/resources/save/results.csv";

            CSVWriter writer = new CSVWriter(new FileWriter(csv, true), ';', CSVWriter.NO_QUOTE_CHARACTER);

            String[] record = (Nom + ";" + Victoires + ";" + Defaites + ";" + Parties + ";" + Ratio + ";" + Score).split(";");

            writer.writeNext(record);

            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Permet d'effacer les données d'avant pour avoir un csv clair.
     */
    public void clearCsv() {
        try {
            FileWriter fw = new FileWriter("src/main/resources/save/results.csv", false);

            fw.write("Nom;Victoires;Défaites;Parties;Ratio;ScoreMoyen\n");

            fw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
