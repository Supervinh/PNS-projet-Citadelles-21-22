package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.Statistique;

import java.io.FileWriter;

/**
 * Permet d'écrire dans un fichier csv.
 */
public class CsvEcriture {

    private final String pathFicher;

    public CsvEcriture(String pathFicher) {
        this.pathFicher = pathFicher;
    }

    /**
     * Permet d'écrire dans le fichier csv.
     *
     * @param Nom       Le nom du joueur.
     * @param Victoires Le nombre de victoires du joueur.
     * @param Defaites  Le nombre de défaites du joueur.
     * @param Parties   Le nombre de parties total joué.
     * @param Ratio     Le ratio de victoire du joueur.
     * @param Score     Le score moyen du joueur.
     */
    public void ecrireStatistiques(String Nom, int Victoires, int Defaites, int Parties, String Ratio, String Score) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(this.pathFicher, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
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
            FileWriter fw = new FileWriter(this.pathFicher, false);
            StringBuilder title = new StringBuilder();
            for (String t : new Statistique(this.pathFicher).getTitre()) {
                title.append(t).append(";");
            }
            title.deleteCharAt(title.length() - 1).append("\n");
            fw.write(title.toString());
            fw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
