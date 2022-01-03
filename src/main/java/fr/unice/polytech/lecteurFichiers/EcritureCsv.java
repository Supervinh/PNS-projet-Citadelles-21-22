package fr.unice.polytech.lecteurFichiers;


import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class EcritureCsv {

        public void ecrireStatistiques(String Nom, int Victoires, int Defaites, int Parties, double Ratio) throws Exception
        {
            String csv = "src/main/resources/save/results.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv,true), ',',  CSVWriter.NO_QUOTE_CHARACTER);

            String [] record = (Nom+","+Victoires+","+Defaites+","+Parties+","+Ratio).split(",");

            writer.writeNext(record);

            writer.close();
        }

    public static void main(String[] args) throws Exception {
        EcritureCsv ecrire = new EcritureCsv();
        CsvReader lire = new CsvReader();

        ecrire.ecrireStatistiques("Bob", 2,2,4,0.5);
    }
}
