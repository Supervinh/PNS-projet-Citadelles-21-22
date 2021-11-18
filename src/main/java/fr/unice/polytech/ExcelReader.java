package fr.unice.polytech;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelReader {
    private final File excelCardFile = new File("data/Cards.xlsx");
    private final File excelCharacterFile = new File("data/Characters.xlsx");

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

    public void printExcel(File file) {
        try {
            XSSFSheet sheet = fileToSheet(file); //creating a Sheet object to retrieve object
            for (Row row : sheet) {     //iterating over excel file
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING ->    //field that represents string cell type
                                System.out.print(cell.getStringCellValue() + "\t\t\t");
                        case Cell.CELL_TYPE_NUMERIC ->    //field that represents number cell type
                                System.out.print(cell.getNumericCellValue() + "\t\t\t");
                        default -> {
                        }
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getExcelCardFile() {
        return this.excelCardFile;
    }

    public File getExcelCharacterFile() {
        return this.excelCharacterFile;
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
                    row.getCell(3).getStringCellValue()));
        }
        return personnagesTemp;
    }
}
