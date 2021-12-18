package fr.unice.polytech.strategie;

import fr.unice.polytech.CartePersonnage;
import fr.unice.polytech.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public interface IStrategie {

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
            // Choisir Personnage en Fonction des Gemmes
            Map<String, Long> gemmes = joueur.getQuartiersConstruits().stream().collect(Collectors.groupingBy(CarteQuartier::getGemme, Collectors.counting()));
            if (gemmes.values().stream().anyMatch(count -> count > 2)) {
                int gemmeCountMax = gemmes.values().stream().mapToInt(Long::intValue).max().orElse(0);
                String gemmeMax = gemmes.entrySet().stream().filter(entry -> entry.getValue() == gemmeCountMax).map(Map.Entry::getKey).findFirst().orElse("");
                choixPersonnage = Objects.requireNonNull(MoteurDeJeu.deck.getPersonnagesPossibles().stream().filter(cp -> cp.getGemme().equals(gemmeMax)).findFirst().orElse(null)).getNom();
            }
            // Manque Assasin & Architecte A Faire.
            String finalChoixPersonnage = choixPersonnage;
            return personnages.stream().filter(cp -> cp.getNom().equals(finalChoixPersonnage)).findAny().orElseGet(
                    () -> personnageTemp.get(new Random().nextInt(personnageTemp.size())));
        } else {
            return null;
        }
    }

    default CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> cibles) {
        ArrayList<CartePersonnage> ciblesTemp = new ArrayList<>(cibles);
        ciblesTemp.remove(joueur.getPersonnage());
        if (ciblesTemp.size() > 0) {
            return ciblesTemp.get(new Random().nextInt(ciblesTemp.size()));
        } else {
            return null;
        }
    }

    default Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        ArrayList<Joueur> ciblesTemp = new ArrayList<>(cibles);
        ciblesTemp.remove(joueur);
        if (ciblesTemp.size() > 0) {
            return ciblesTemp.get(new Random().nextInt(ciblesTemp.size()));
        } else {
            return null;
        }
    }

    default CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> quartiersTemp = new ArrayList<>(quartiers);
        joueur.getQuartiersConstruits().forEach(quartiersTemp::remove);
        if (quartiersTemp.size() > 0) {
            return quartiersTemp.get(new Random().nextInt(quartiersTemp.size()));
        } else {
            return null;
        }
    }

    default String nomStrategie() {
        return "Par Default (Random)";
    }
}
