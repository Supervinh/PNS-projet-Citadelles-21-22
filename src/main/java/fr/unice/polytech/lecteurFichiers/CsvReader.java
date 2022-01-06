package fr.unice.polytech.lecteurFichiers;

import au.com.bytecode.opencsv.CSVReader;
import fr.unice.polytech.Affichage;

import java.io.File;
import java.io.FileReader;

/**
 * Permet de lire un fichier csv.
 */
public class CsvReader {

    private final String pathFicher;
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
     * On instancie le tableau avec le nombre de lignes du fichier.
     */
    public CsvReader(String pathFicher) {
        this.pathFicher = pathFicher;
        try {
            this.calculeLigne();
            this.data = new String[this.nombreLigne][5];
        } catch (Exception e) {
            Affichage.warning(e.getMessage());
        }
    }

    /**
     * Permet de récupérer le tableau de valeurs du fichier csv.
     *
     * @return Le tableau de valeurs.
     */
    public String[][] getData() {
        lireStatistiques();
        return this.data;
    }

    /**
     * Permet de lire le fichier csv et d'initialiser le tableau avec les valeurs du fichier csv.
     */
    @SuppressWarnings("resource")
    public void lireStatistiques() {
        String[] nextLine;
        int lineNum = 0;
        try {
            this.reader = new CSVReader(new FileReader(this.pathFicher), ';', ' ', 1);
            while ((nextLine = this.reader.readNext()) != null) {
                this.data[lineNum++] = nextLine;
            }
            this.reader.close();
        } catch (Exception e) {
            Affichage.warning(e.getMessage());
        }
    }

    /**
     * Calcule le nombre de lignes dans le fichier.
     */
    public void calculeLigne() {
        try {
            this.reader = new CSVReader(new FileReader(this.pathFicher), ';', ' ', 1);
            while ((this.reader.readNext()) != null) {
                this.nombreLigne++;
            }
            this.reader.close();
        } catch (Exception e) {
            Affichage.warning(e.getMessage());
            try {
                File newFile = new File(this.pathFicher);
                if (newFile.createNewFile()) {
                    Affichage.warning("File created: " + newFile.getName());
                }
            } catch (Exception fatal) {
                Affichage.severe(e.getMessage());
            }
        }
    }
}
