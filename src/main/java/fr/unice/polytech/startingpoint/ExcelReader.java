package fr.unice.polytech.startingpoint;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelReader {
    private File excelCardFile = new File("data/Cards.xlsx");
    private File excelCharacterFile = new File("data/Characters.xlsx");

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

    public ArrayList<CarteQuartier> recupererQuartiers() {

        return null;
    }

    public File getExcelCardFile() {
        return this.excelCardFile;
    }

    public File getExcelCharacterFile() {
        return this.excelCharacterFile;
    }
}
