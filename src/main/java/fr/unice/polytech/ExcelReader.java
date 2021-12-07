package fr.unice.polytech;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class ExcelReader {
    private final File excelCardFile = new File("data/Cards.xlsx");
    private final File excelCharacterFile = new File("data/Characters.xlsx");
    private final File excelRandomNames = new File("data/Names.xlsx");

    public ExcelReader() {
    }

    public XSSFSheet fileToSheet(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return new XSSFWorkbook(fis).getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public String getRandomName() {
        XSSFSheet sheet = fileToSheet(this.excelRandomNames);
        String name = sheet.getRow(new Random().nextInt(sheet.getLastRowNum())).getCell(0).getStringCellValue().replace("\u008F", "");
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
