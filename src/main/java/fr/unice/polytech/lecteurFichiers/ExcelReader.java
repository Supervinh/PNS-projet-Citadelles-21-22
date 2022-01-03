package fr.unice.polytech.lecteurFichiers;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe permettant d'extraire les données d'un fichier excel.
 */
public class ExcelReader {
    /**
     * Fichier excel contenant les informations sur les cartes quartiers.
     */
    private final File excelCardFile = new File("data/Cards.xlsx");

    /**
     * Fichier excel contenant les informations sur les cartes personnages.
     */
    private final File excelCharacterFile = new File("data/Characters.xlsx");

    /**
     * Fichier excel contenant une liste de nom pour les joueurs.
     */
    private final File excelRandomNames = new File("data/Names.xlsx");

    /**
     * Le constructeur vide du lecteur de fichiers excel.
     */
    public ExcelReader() {
    }

    /**
     * Met le fichier excel dans un format que l'on peut manipuler.
     *
     * @param file Le fichier excel.
     * @return Un Sheet excel manipulable.
     */
    public XSSFSheet fileToSheet(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return new XSSFWorkbook(fis).getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère les informations des cartes quartiers et les initialisent en tant qu'objet CarteQuartier et les insèrent dans une liste.
     *
     * @return La liste avec toutes les cartes quartiers.
     */
    public ArrayList<CarteQuartier> recupererQuartiers() {
        ArrayList<CarteQuartier> quartiersTemp = new ArrayList<>();
        XSSFSheet sheet = fileToSheet(this.excelCardFile);
        int count = 0;
        for (Row row : sheet) {
            count++;
            if (count == 1) continue;
            for (int i = 1; i <= row.getCell(2).getNumericCellValue(); i++) {
                quartiersTemp.add(new CarteQuartier(
                        row.getCell(0).getNumericCellValue() + (0.1 * i),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getNumericCellValue(),
                        row.getCell(5).getStringCellValue()));
            }
        }
        return quartiersTemp;
    }

    /**
     * Récupère les informations des cartes personnages et les initialisent en tant qu'objet CartePersonnage et les insèrent dans une liste.
     *
     * @return La liste avec toutes les cartes personnages.
     */
    public ArrayList<CartePersonnage> recupererPersonnage() {
        ArrayList<CartePersonnage> personnagesTemp = new ArrayList<>();
        XSSFSheet sheet = fileToSheet(this.excelCharacterFile);
        int count = 0;
        for (Row row : sheet) {
            count++;
            if (count == 1) continue;
            personnagesTemp.add(new CartePersonnage(
                    row.getCell(0).getNumericCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getStringCellValue(),
                    row.getCell(3).getStringCellValue(),
                    row.getCell(4).getStringCellValue()));
        }
        return personnagesTemp;
    }

    /**
     * Récupère un nom au hasard dans le fichier de noms.
     *
     * @return Le nom du joueur.
     */
    public String getRandomName() {
        XSSFSheet sheet = fileToSheet(this.excelRandomNames);
        String name = sheet.getRow(new Random().nextInt(sheet.getLastRowNum())).getCell(0).getStringCellValue().replace("\u008F", "");
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
