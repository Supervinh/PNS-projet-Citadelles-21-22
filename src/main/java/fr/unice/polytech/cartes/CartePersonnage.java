package fr.unice.polytech.cartes;

import fr.unice.polytech.couleur.CouleurConsole;

/**
 * Classe permettant d'initialiser les différents personnages du jeu.
 */
public class CartePersonnage implements Comparable<CartePersonnage> {
    /**
     * L'identifiant du personnage.
     */
    private final double id;

    /**
     * Le nom du personnage.
     */
    private final String nom;

    /**
     * La couleur de la gemme de la carte du personnage.
     */
    private final String gemme;

    /**
     * La description des pouvoirs du personnage.
     */
    private final String description;

    /**
     * L'article a afficher devant un nom.
     */
    private final String article;

    /**
     * Le constructeur du personnage avec son identifiant, son nom et sa gemme.
     *
     * @param id    L'identifiant du personnage.
     * @param nom   Le nom du personnage.
     * @param gemme La gemme de la carte du personnage.
     */
    public CartePersonnage(double id, String nom, String gemme) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = "";
        this.article = "";
    }

    /**
     * Le constructeur du personnage avec son identifiant, son nom, sa gemme et sa description.
     *
     * @param id          L'identifiant du personnage.
     * @param nom         Le nom du personnage.
     * @param gemme       La gemme de la carte du personnage.
     * @param description La description du pouvoir du personnage.
     */
    public CartePersonnage(double id, String nom, String gemme, String description) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = description;
        this.article = "";
    }

    /**
     * Le constructeur du personnage avec son identifiant, son nom, sa gemme, sa description et son article.
     *
     * @param id          L'identifiant du personnage.
     * @param nom         Le nom du personnage.
     * @param gemme       La gemme de la carte du personnage.
     * @param description La description du pouvoir du personnage.
     * @param article     L'article du personnage.
     */
    public CartePersonnage(double id, String nom, String gemme, String description, String article) {
        this.id = id;
        this.nom = nom;
        this.gemme = gemme;
        this.description = description;
        this.article = article;
    }

    /**
     * Permet de récupérer l'identifiant du personnage.
     *
     * @return L'identifiant du personnage.
     */
    public double getId() {
        return this.id;
    }

    /**
     * Permet de récupérer l'identifiant du personnage, mais avec des couleurs pour l'affichage.
     *
     * @return L'identifiant du personnage en turquoise.
     */
    public String getIdColoured() {
        return CouleurConsole.turquoise("" + this.id);
    }

    /**
     * Permet de récupérer le nom du personnage.
     *
     * @return Le nom du personnage.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Permet de récupérer le nom du personnage, mais avec des couleurs pour l'affichage.
     *
     * @return Le nom du personnage en rouge.
     */
    public String getNomColoured() {
        return CouleurConsole.red(this.nom);
    }

    /**
     * Permet de récupérer la couleur de la gemme de la carte du personnage.
     *
     * @return La gemme de la carte personnage.
     */
    public String getGemme() {
        return this.gemme;
    }

    /**
     * Permet de récupérer la couleur de la gemme de la carte du personnage, mais avec des couleurs pour l'affichage.
     *
     * @return La gemme de la carte personnage en violet.
     */
    public String getGemmeColoured() {
        return CouleurConsole.purple(this.gemme);
    }

    /**
     * Permet de récupérer la description du pouvoir du personnage.
     *
     * @return La description du pouvoir du personnage.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Permet de récupérer la description du pouvoir du personnage, mais avec des couleurs pour l'affichage.
     *
     * @return La description du pouvoir du personnage en gris.
     */
    public String getDescriptionColoured() {
        return CouleurConsole.grey(this.description);
    }

    /**
     * Permet de récupérer l'article du personnage.
     *
     * @return L'article du personnage.
     */
    public String getArticle() {
        return this.article;
    }

    /**
     * Affichage de toutes les informations du personnage, son identifiant, son nom, sa gemme et sa description.
     *
     * @return Les informations du personnage.
     */
    @Override
    public String toString() {
        return "CartePersonnage{" +
                "id=" + this.getIdColoured() +
                ", nom=" + this.getNomColoured() +
                ", gemme=" + this.getGemmeColoured() +
                ", description=" + this.getDescriptionColoured() +
                '}';
    }

    /**
     * Compare les identifiants de deux cartes personnages.
     *
     * @param cp L'autre carte avec laquelle on compare.
     * @return Si c'est égal à 0, c'est que les deux cartes sont les mêmes sinon c'est deux cartes différentes.
     */
    @Override
    public int compareTo(CartePersonnage cp) {
        return (int) (this.id - cp.id);
    }
}
