package fr.unice.polytech.strategie;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Interface pour les différentes stratégies de jeu.
 */
public interface IStrategie {

    /**
     * Permet le choix du personnage en fonction de ce que l'on a en main au niveau des quartiers et de l'or.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte de personnage choisie.
     */
    default CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        ArrayList<CartePersonnage> personnageTemp = new ArrayList<>(personnages);
        if (personnageTemp.size() > 0) {
            String choixPersonnage = "";
            if (joueur.getOr() < 3 && MoteurDeJeu.joueurs.stream().mapToInt(Joueur::getOr).max().orElse(0) > 4) {
                choixPersonnage = "Voleur";
            }
            if (joueur.getQuartiers().size() < 2 && MoteurDeJeu.joueurs.stream().mapToInt(j -> j.getQuartiers().size()).max().orElse(0) > 3) {
                choixPersonnage = "Magicien";
            }
            if (joueur.getQuartiers().size() < 2 || (joueur.nombre2QuartiersConstructible() > 1 && joueur.getOr() > 4)) {
                choixPersonnage = "Architecte";
            }

            // Choisir Personnage en Fonction des Gemmes
            Map<String, Long> gemmes = joueur.getQuartiersConstruits().stream().collect(Collectors.groupingBy(CarteQuartier::getGemme, Collectors.counting()));
            if (gemmes.values().stream().anyMatch(count -> count > 2)) {
                int gemmeCountMax = gemmes.values().stream().mapToInt(Long::intValue).max().orElse(0);
                String gemmeMax = gemmes.entrySet().stream().filter(entry -> entry.getValue() == gemmeCountMax).map(Map.Entry::getKey).findFirst().orElse("");
                CartePersonnage choix = MoteurDeJeu.deck.getPersonnagesPossibles().stream().filter(cp -> cp.getGemme().equals(gemmeMax)).findFirst().orElse(null);
                if (choix != null) {
                    choixPersonnage = choix.getNom();
                }
            }

            String finalChoixPersonnage = choixPersonnage;
            return personnages.stream().filter(cp -> cp.getNom().equals(finalChoixPersonnage)).findAny().orElseGet(
                    () -> personnageTemp.get(new Random().nextInt(personnageTemp.size())));
        } else {
            return null;
        }
    }

    /**
     * Permet de cibler un personnage autre que soi-même.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des personnages.
     * @return La cible visée.
     */
    default CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> cibles) {
        ArrayList<CartePersonnage> ciblesTemp = new ArrayList<>(cibles);
        ciblesTemp.remove(joueur.getPersonnage());
        if (ciblesTemp.size() > 0) {
            return ciblesTemp.get(new Random().nextInt(ciblesTemp.size()));
        } else {
            return null;
        }
    }

    /**
     * Permet de cibler un joueur autre que soi-même.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des joueurs.
     * @return La cible visée.
     */
    default Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        ArrayList<Joueur> ciblesTemp = new ArrayList<>(cibles);
        ciblesTemp.remove(joueur);
        if (ciblesTemp.size() > 0) {
            return ciblesTemp.get(new Random().nextInt(ciblesTemp.size()));
        } else {
            return null;
        }
    }

    /**
     * Permet de cibler une carte quartier.
     *
     * @param joueur    Le joueur qui joue.
     * @param quartiers La liste des quartiers.
     * @return Le quartier visé.
     */
    default CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> quartiersTemp = new ArrayList<>(quartiers);
        joueur.getQuartiersConstruits().forEach(quartiersTemp::remove);
        if (quartiersTemp.size() > 0) {
            return quartiersTemp.get(new Random().nextInt(quartiersTemp.size()));
        } else {
            return null;
        }
    }

    /**
     * Donne le nom de la stratégie utilisée.
     *
     * @return Le nom de la stratégie.
     */
    default String nomStrategie() {
        return "Par Default (Random)";
    }
}
