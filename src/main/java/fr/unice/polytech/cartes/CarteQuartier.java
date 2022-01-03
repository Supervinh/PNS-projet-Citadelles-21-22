package fr.unice.polytech.cartes;

import fr.unice.polytech.couleur.CouleurConsole;

/**
 * Classe permettant d'initialiser les différents quartiers du jeu.
 */
public class CarteQuartier implements Comparable<CarteQuartier> {
    /**
     * L'identifiant du quartier.
     */
    private final double id;

    /**
     * Le nom du quartier.
     */
    private final String nom;
    /**
     * Le prix du quartier.
     */
    private final int prix;
    /**
     * La description du pouvoir du quartier.
     */
    private final String description;
    /**
     * La couleur de la gemme de la carte quartier.
     */
    private String gemme;

    /**
     * Le constructeur du quartier avec son identifiant, son nom, sa gemme et son prix.
     *
     * @param id    L'identifiant du quartier.
     * @param nom   Le nom du quartier.
     * @param gemme La couleur de la gemme du quartier.
     * @param prix  Le prix du quartier.
     */
    public CarteQuartier(double id, String nom, String gemme, double prix) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.prix = (int) prix;
        this.description = "None";
    }

    /**
     * Le constructeur du quartier avec son identifiant, son nom, sa gemme, son prix et sa description.
     *
     * @param id          L'identifiant du quartier.
     * @param nom         Le nom du quartier.
     * @param gemme       La couleur de la gemme du quartier.
     * @param prix        Le prix du quartier.
     * @param description La description du pouvoir du quartier.
     */
    public CarteQuartier(double id, String nom, String gemme, double prix, String description) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.prix = (int) prix;
        this.description = description;
    }

    /**
     * Permet de récupérer l'identifiant du quartier.
     *
     * @return L'identifiant du quartier.
     */
    public double getId() {
        return this.id;
    }

    /**
     * Permet de récupérer l'identifiant du quartier, mais avec des couleurs pour l'affichage.
     *
     * @return L'identifiant du quartier en turquoise.
     */
    public String getIdColoured() {
        return CouleurConsole.printTurquoise("" + this.id);
    }

    /**
     * Permet de récupérer le nom du quartier.
     *
     * @return Le nom du quartier.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Permet de récupérer le nom du quartier, mais avec des couleurs pour l'affichage.
     *
     * @return Le nom du quartier en vert.
     */
    public String getNomColoured() {
        return CouleurConsole.printGreen(this.nom);
    }

    /**
     * Permet de récupérer la couleur de la gemme du quartier.
     *
     * @return La couleur de la gemme du quartier.
     */
    public String getGemme() {
        return this.gemme;
    }

    /**
     * Changer la gemme du quartier.
     *
     * @param gemme La couleur de la gemme du quartier.
     */
    public void setGemme(String gemme) {
        this.gemme = gemme;
    }

    /**
     * Permet de récupérer la couleur de la gemme du quartier, mais avec des couleurs pour l'affichage.
     *
     * @return La couleur de la gemme du quartier en violet.
     */
    public String getGemmeColoured() {
        return CouleurConsole.printPurple(this.gemme);
    }

    /**
     * Permet de récupérer le prix du quartier.
     *
     * @return Le prix du quartier.
     */
    public int getPrix() {
        return this.prix;
    }

    /**
     * Permet de récupérer le prix du quartier, mais avec des couleurs pour l'affichage.
     *
     * @return Le prix du quartier en or.
     */
    public String getPrixColoured() {
        return CouleurConsole.printGold("" + this.prix) + " pièce" + (this.prix > 1 ? "s" : "") + " d'" + CouleurConsole.printGold("Or");
    }

    /**
     * Permet de récupérer la description du pouvoir du quartier.
     *
     * @return La description du pouvoir du quartier.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Permet de récupérer la description du pouvoir du quartier, mais avec des couleurs pour l'affichage.
     *
     * @return La description du pouvoir du quartier en gris.
     */
    public String getDescriptionColoured() {
        return CouleurConsole.printGrey(this.description);
    }

    /**
     * Affichage de toutes les informations du quartier, son identifiant, son nom, sa gemme, son prix et sa description.
     *
     * @return Les informations du quartier.
     */
    @Override
    public String toString() {
        return "CarteQuartier{" +
                "id=" + this.getIdColoured() +
                ", nom=" + this.getNomColoured() +
                ", gemme=" + this.getGemmeColoured() +
                ", prix=" + this.getPrixColoured() +
                ", description=" + this.getDescriptionColoured() +
                '}';
    }

    /**
     * Compare le prix du quartier avec celui passer en paramètre.
     *
     * @param cq La carte quartier avec lequel on compare le prix.
     * @return La différence de prix entre les deux quartiers.
     */
    @Override
    public int compareTo(CarteQuartier cq) {
        return cq.prix - this.prix;
    }
}
