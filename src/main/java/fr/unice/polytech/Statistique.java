package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvReader;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Statistique {
    private int nombrePartie = 0;
    private final HashMap<String, Integer> stats = new HashMap<>();

    public void ajoutGagnant(Joueur joueur) {
        try {
            int nombreVictoire = this.stats.get(joueur.getNom());
            this.stats.put(joueur.getNom(), ++nombreVictoire);

        } catch (Exception e) {
            this.stats.put(joueur.getNom(), 1);
        }
        this.nombrePartie++;
    }

    public void ajoutAuxCSVReader() {
        CsvReader csvReader = new CsvReader();
        // nom, Vic, def, par, ratio
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.stats.forEach((key, victoire) -> System.out.println(Arrays.toString(new String[]{key, String.valueOf(victoire), String.valueOf(this.nombrePartie - victoire), String.valueOf(this.nombrePartie), df.format(victoire / (float) (this.nombrePartie))})));
    }

    @Override
    public String toString() {
        return "Statistique{" +
                "stats=" + stats +
                '}';
    }
}
