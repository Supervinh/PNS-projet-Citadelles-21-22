package fr.unice.polytech.startingpoint;

public class Main {


    public static String hello() {
        return "Hello World!";
    }

    public static void main(String... args) {
        System.out.println(hello());

        ExcelReader Er = new ExcelReader();
//        Er.printExcel(Er.getExcelCardFile());
        Er.printExcel(Er.getExcelCharacterFile());

        Joueur J1 = new Joueur("Test");
        System.out.println(J1);

        CarteQuartier bat = new CarteQuartier(0.1, "Temple", 3, "Religion", 1);
        System.out.println(bat);


    }

}