package fr.unice.polytech;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Classe permettant d'initialiser le deck contenant les cartes personnages et les cartes quartiers du jeu.
 */
public class Deck {
    /**
     * La liste des cartes quartiers dans le jeu.
     */
    private final ArrayList<CarteQuartier> quartiers;

    /**
     * La liste des cartes personnages dans le jeu.
     */
    private final ArrayList<CartePersonnage> personnages;

    /**
     * La liste des quartiers possible.
     */
    private final ArrayList<CarteQuartier> quartiersPossibles;

    /**
     * La liste des personnages possible.
     */
    private final ArrayList<CartePersonnage> personnagesPossibles;


    /**
     * Le constructeur où on initialise les pioches.
     * On récupère les cartes grâce à une lecture des fichiers excel.
     * On mélange ensuite les deux pioches.
     */
    public Deck() {
        ExcelReader ER = new ExcelReader();
        this.quartiers = ER.recupererQuartiers();
        this.personnages = ER.recupererPersonnage();
        this.quartiersPossibles = new ArrayList<>(List.copyOf(this.quartiers));
        this.personnagesPossibles = new ArrayList<>(List.copyOf(this.personnages));
        this.melagerArrayList(this.quartiers);
        this.melagerArrayList(this.personnages);
    }

    /**
     * Permet de récupérer la liste des cartes quartiers.
     *
     * @return La liste des cartes quartiers.
     */
    public ArrayList<CarteQuartier> getQuartiers() {
        return quartiers;
    }

    /**
     * Permet de récupérer la liste des cartes personnages.
     *
     * @return La liste des cartes personnages.
     */
    public ArrayList<CartePersonnage> getPersonnages() {
        return personnages;
    }

    /**
     * Permet de récupérer la liste des cartes quartiers possibles.
     *
     * @return La liste des cartes quartiers possibles.
     */
    public ArrayList<CarteQuartier> getQuartiersPossibles() {
        return quartiersPossibles;
    }

    /**
     * Permet de récupérer la liste des cartes personnages possibles.
     *
     * @return La liste des cartes personnages possibles.
     */
    public ArrayList<CartePersonnage> getPersonnagesPossibles() {
        return personnagesPossibles;
    }

    /**
     * Mélange la liste que l'on passe en paramètre.
     *
     * @param arrayList La pioche a mélanger
     */
    public void melagerArrayList(ArrayList<?> arrayList) {
        for (Object e : arrayList) {
            Collections.shuffle(arrayList);
        }
    }

    /**
     * Pioche une carte quartier si la pioche n'est pas vide, et on l'enlève de la pioche.
     *
     * @return La carte quartier piochée si la pioche n'est pas vide sinon on renvoie null.
     */
    public CarteQuartier piocherQuartier() {
        if (!this.quartiers.isEmpty()) {
            return this.quartiers.remove(0);
        } else {
            System.out.println("Plus de Quartiers...");
            return null;
        }
    }

    /**
     * Pioche une carte personnage si la pioche n'est pas vide, et on l'enlève de la pioche.
     *
     * @return La carte personnage piochée si la pioche n'est pas vide sinon on renvoie null.
     */
    public CartePersonnage piocherPersonnage() {
        if (!this.personnages.isEmpty()) {
            return this.personnages.remove(new Random().nextInt(this.personnages.size()));
        } else {
            System.out.println("Plus de Personnages...");
            System.exit(0);
            return null;
        }
    }

    /**
     * Ajoute une carte quartier dans la pioche des quartiers si elle n'y est pas déjà.
     *
     * @param cq La carte quartier a ajouter.
     */
    public void ajouterQuartierDeck(CarteQuartier cq) {
        if (!this.quartiers.contains(cq)) {
            this.quartiers.add(cq);
        } else {
            if (cq != null) System.out.println("Le Deck contient déjà: " + cq.getNomColoured());
        }
    }

    /**
     * Ajoute une carte personnage dans la pioche des personnages si elle n'y est pas déjà.
     *
     * @param personnage La carte personnage à ajouter.
     */
    public void ajoutePersonnage(CartePersonnage personnage) {
        if (!this.personnages.contains(personnage)) {
            this.personnages.add(personnage);
        } else {
            System.out.println("Le Deck Contiens deja: " + personnage.getNomColoured());
        }
    }

    public boolean resteQuartier() {
        return this.quartiers.size() > 0;
    }

    /**
     * Affichage des deux pioches des quartiers et des personnages.
     *
     * @return Les informations des deux pioches du jeu.
     */
    @Override
    public String toString() {
        return "Jeu2Carte{" +
                "quartiers=" + quartiers +
                ", personnages=" + personnages +
                '}';
    }
}
