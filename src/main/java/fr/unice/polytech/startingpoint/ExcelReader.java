package fr.unice.polytech.startingpoint;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelReader
{
    public static void main(String[] args)
    {
        String file_type = "Cards";
        try
        {
            File file = new File("data/" + file_type + ".xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(fis);    //creating Workbook instance that refers to .xlsx file
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
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
                System.out.println("");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}