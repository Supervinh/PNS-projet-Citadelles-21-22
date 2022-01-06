package fr.unice.polytech;

import fr.unice.polytech.lecteurFichiers.CsvEcriture;
import fr.unice.polytech.lecteurFichiers.CsvReader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe qui permet le calcul des statistiques.
 */
public class Statistique {
    /**
     * HashMap permettant de connaitre le nombre de victoires des joueurs.
     */
    private final HashMap<String, Integer> statistiqueVictoireData = new HashMap<>();

    /**
     * HashMap permettant de connaitre la moyenne des scores des joueurs.
     */
    private final HashMap<String, Double> statistiqueScoreData = new HashMap<>();

    /**
     * Nom des colonnes de notre tableau de statistiques.
     */
    private final String[] titre = new String[]{"Nom des Bots/IA", "Victoires", "Défaites", "Parties", "Ratio", "Points Moy"};

    /**
     * Largeur de la marge.
     */
    private final int marge = 4;

    /**
     * Le tableau des informations que l'on récupère du csv.
     */
    private String[][] data;

    /**
     * Le Nom du fichier dans le quelle on sauvegarde les données.
     */
    private final String nomFicher;

    public Statistique(String nomFicher) {
        this.nomFicher = "src/main/resources/save/" + nomFicher + ".csv";
    }

    /**
     * Permet de récupérer une liste des titres du tableau.
     *
     * @return Les Titres du tableau.
     */
    public String[] getTitre() {
        return this.titre;
    }

    /**
     * Ajoute les statistiques que nous avons calculées.
     *
     * @param moteurDeJeu Le moteur du jeu.
     */
    public void ajoutStats(MoteurDeJeu moteurDeJeu) {
        this.ajoutGagnant(moteurDeJeu.obtenirGagnant(MoteurDeJeu.joueurs));
        this.ajoutScore(MoteurDeJeu.joueurs);
    }

    /**
     * Incrémente le nombre de victoires du joueur gagnant.
     *
     * @param joueur Le joueur.
     */
    private void ajoutGagnant(Joueur joueur) {
        String nom = this.nomAvecStrategie(joueur.getNom());
        if (this.statistiqueVictoireData.containsKey(nom)) {
            int nombreVictoire = this.statistiqueVictoireData.get(nom);
            this.statistiqueVictoireData.replace(nom, ++nombreVictoire);
        } else {
            this.statistiqueVictoireData.put(nom, 1);
        }
    }

    /**
     * Recalcule le score moyen du joueur.
     *
     * @param joueurs Les joueurs de la partie.
     */
    private void ajoutScore(ArrayList<Joueur> joueurs) {
        String nom;
        for (Joueur joueur : joueurs) {
            nom = this.nomAvecStrategie(joueur.getNom());
            if (this.statistiqueScoreData.containsKey(nom)) {
                double scoreMoy = this.statistiqueScoreData.get(nom);
                this.statistiqueScoreData.replace(nom, scoreMoy + (joueur.getPoints() / (double) Main.nombrePartie));
            } else {
                this.statistiqueScoreData.put(nom, joueur.getPoints() / (double) Main.nombrePartie);
            }
        }
    }

    /**
     * Ajoute les statistiques calculées au fichier CSV.
     */
    public void ajoutAuxCSV() {
        CsvReader csvReader = new CsvReader(this.nomFicher);
        CsvEcriture ecritureCsv = new CsvEcriture(this.nomFicher);

        csvReader.lireStatistiques();
        this.data = csvReader.getData();
        ecritureCsv.clearCsv();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        this.rajouteNonGagnant();

        for (int i = 0; i < this.statistiqueVictoireData.size(); i++) {
            for (Map.Entry<String, Integer> entry : this.statistiqueVictoireData.entrySet()) {
                if (entry.getKey().contains("CPU" + (i + 1)) || !entry.getKey().contains("CPU")) {
                    String nom = entry.getKey();
                    int victoireTotal = entry.getValue() + Integer.parseInt(this.getValeurTableau(trouverLigne(data, nom), 1));
                    int partieTotal = Main.nombrePartie + Integer.parseInt(this.getValeurTableau(trouverLigne(data, nom), 3));
                    int defaiteTotal = partieTotal - victoireTotal;
                    String ratio = df.format(victoireTotal / (double) (partieTotal));

                    double moyenCSV = Double.parseDouble(this.getValeurTableau(trouverLigne(data, nom), 5).replace(',', '.'));
                    String scoreMoyenTotal = df.format(0.5 * (this.statistiqueScoreData.get(nom) + (moyenCSV <= 0 ? this.statistiqueScoreData.get(nom) : moyenCSV)));

                    ecritureCsv.ecrireStatistiques(nom, victoireTotal, defaiteTotal, partieTotal, ratio, scoreMoyenTotal);
                }
            }
        }
    }

    /**
     * Rajoute le nombre de défaites.
     */
    private void rajouteNonGagnant() {
        String nom;
        for (Joueur joueur : MoteurDeJeu.joueurs) {
            nom = this.nomAvecStrategie(joueur.getNom());
            if (!this.statistiqueVictoireData.containsKey(nom)) {
                this.statistiqueVictoireData.put(nom, 0);
            }
        }
    }

    /**
     * Récupère les valeurs déjà existantes dans le fichier CSV.
     *
     * @param x La ligne.
     * @param y La colonne.
     * @return L'élément dans la ligne et la colonne donnée du fichier CSV.
     */
    private String getValeurTableau(int x, int y) {
        try {
            return this.data[x][y];
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * Trouve la ligne du tableau ou se trouve le nom donné.
     *
     * @param tableau Le tableau représentant les données du fichier CSV.
     * @param nom     Le nom que l'on cherche.
     * @return La ligne à laquelle se trouve le nom recherché.
     */
    private int trouverLigne(String[][] tableau, String nom) {
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i][0].contains(nom)) {
                return i;
            }
        }
        return tableau.length;
    }

    /**
     * Imprime le tableau sur la sortie standard.
     */
    public void printStatTableau() {
        Affichage.info("");
        String[] pathFicher = this.nomFicher.split("/");
        String t = pathFicher[pathFicher.length - 1];
        pathFicher = t.split("\\.");
        Affichage.titreFormatted("Tableau des Statistiques: " + pathFicher[0]);
        CsvReader csvReader = new CsvReader(this.nomFicher);
        Object[] titre = this.titre;
        this.data = csvReader.getData();

        Affichage.titreFormatted(String.format("%" + (this.largeurColonne(0) - this.marge) + "s%" + this.largeurColonne(1) + "s%" + this.largeurColonne(2) + "s%" + this.largeurColonne(3) + "s%" + this.largeurColonne(4) + "s%" + this.largeurColonne(5) + "s", titre));

        StringBuilder separateur = new StringBuilder();
        for (int i = 0; i < this.titre.length; i++) {
            separateur.append("-".repeat(i == 0 ? this.largeurColonne(i) - this.marge : this.largeurColonne(i)));
        }
        Affichage.ligneFormatted(separateur.toString());

        for (final Object[] row : this.data) {
            Affichage.ligneFormatted(String.format("%" + (this.largeurColonne(0) - this.marge) + "s%" + this.largeurColonne(1) + "s%" + this.largeurColonne(2) + "s%" + this.largeurColonne(3) + "s%" + this.largeurColonne(4) + "s%" + this.largeurColonne(5) + "s", row));
        }
    }

    /**
     * Calcule la longueur de la colonne, ce qui va permettre d'avoir un affichage propre.
     *
     * @param numColonne Largeur de la colonne.
     * @return La nouvelle largeur de la colonne.
     */
    private int largeurColonne(int numColonne) {
        int largeur = 0;
        for (String[] ligne : this.data) {
            largeur = Math.max(largeur, ligne[numColonne].length());
        }
        largeur = Math.max(largeur, this.titre[numColonne].length());
        return largeur + this.marge;
    }

    /**
     * Affiche le nom des joueurs avec leur stratégie.
     *
     * @param nom Nom du joueur.
     * @return Le nom du joueur et de sa stratégie.
     */
    private String nomAvecStrategie(String nom) {
        Joueur joueur = MoteurDeJeu.joueurs.stream().filter(j -> j.getNom().equals(nom)).findFirst().orElse(null);
        if (joueur != null) {
            return joueur.getNomStrategie() + " - " + nom;
        }
        return nom;
    }

    /**
     * Affiche le nombre de victoires.
     *
     * @return L'information sur le nombre de victoires.
     */
    @Override
    public String toString() {
        return "nombreVictoire=" + statistiqueVictoireData + '}';
    }
}
