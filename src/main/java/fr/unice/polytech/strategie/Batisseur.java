package fr.unice.polytech.strategie;

import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;
import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La stratégie qui récolte le plus d'or pour pouvoir construire.
 */
public class Batisseur implements IStrategie {

    /**
     * Permet de faire le bon choix du personnage en fonction de ce que le joueur a dans sa main.
     * On calcule le nombre de cartes avec les gemmes "commerce et artisanat" et "noblesse".
     * Si le joueur a au moins une carte noblesse et que le joueur n'est pas roi, le joueur choisit le roi.
     * Si le joueur a plus de une carte commerce alors le joueur choisit le marchand.
     * Si le joueur a plus de 2 cartes quartiers en main et qu'il a plus de 5 pièces d'or il choisit architecte.
     * Sinon le joueur choisit aléatoirement.
     *
     * @param joueur      Le joueur à jouer.
     * @param personnages Les cartes de personnages que l'on peut encore piocher.
     * @return La carte du personnage choisie.
     */
    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = null;
        int quartierCommerce = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals("Commerce et Artisanat")).count();
        int quartierNoblesse = (int) joueur.getQuartiersConstruits().stream().filter(quartier -> quartier.getGemme().equals("Noblesse")).count();

        if (quartierNoblesse > 0 && !joueur.isRoi())
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Roi")).findAny().orElse(null);
        if (quartierCommerce > 0 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Marchand")).findAny().orElse(null);
        if (joueur.getQuartiers().size() > 2 && joueur.getOr() > 5 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Architecte")).findAny().orElse(null);
        if (choix == null) choix = IStrategie.super.choixDePersonnage(joueur, personnages);
        return choix;
    }

    /**
     * Permet de choisir la cible d'une carte personnage.
     * Si le joueur a pioché voleur, on va viser l'architecte ou le marchand.
     * Sinon on choisit aléatoirement.
     *
     * @param joueur     Le joueur qui joue.
     * @param ciblesTemp Les cibles de personnages.
     * @return La carte personnage ciblée.
     */
    @Override
    public CartePersonnage choixDeCibleCartePersonnage(Joueur joueur, ArrayList<CartePersonnage> ciblesTemp) {
        CartePersonnage personnageCible = null;
        if (joueur.getPersonnage().getNom().equals("Voleur")) {
            personnageCible = MoteurDeJeu.deck.getPersonnages().stream().filter(p -> p.getNom().equals("Architecte") || p.getNom().equals("Marchand")).findFirst().orElse(null);
        }
        if (personnageCible == null) {
            return IStrategie.super.choixDeCibleCartePersonnage(joueur, ciblesTemp);
        }
        return personnageCible;
    }


    /**
     * Choisit le joueur avec le maximum de point sinon il en choisit un aléatoirement.
     *
     * @param joueur Le joueur qui joue.
     * @param cibles La liste des joueurs.
     * @return Le joueur ciblé.
     */
    @Override
    public Joueur choixDeCibleJoueur(Joueur joueur, ArrayList<Joueur> cibles) {
        MoteurDeJeu.joueurs.forEach(Joueur::calculePoints);
        int scoreMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getPoints).max().orElse(0);
        return MoteurDeJeu.joueurs.stream().filter(j -> j.getPoints() == scoreMax).findFirst().orElseGet(() -> IStrategie.super.choixDeCibleJoueur(joueur, cibles));
    }

    /**
     * Choisit un quartier Noblesse ou Commerce et Artisanat
     * Sinon il choisit celui qui est le moins cher possible.
     *
     * @param joueur    Le joueur qui joue.
     * @param quartiers La liste des quartiers.
     * @return La carte quartier choisie.
     */
    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers);
        Collections.sort(carteQuartiers);
        for (CarteQuartier q : carteQuartiers) {
            if(q.getGemme().equals("Noblesse")){
                carteQuartiers.removeIf(qu -> !qu.getGemme().equals("Noblesse"));
                return carteQuartiers.get(carteQuartiers.size() - 1);
            }
            if (q.getGemme().equals("Commerce et Artisanat")) {
                carteQuartiers.removeIf(qu -> !qu.getGemme().equals("Commerce et Artisanat"));
                return carteQuartiers.get(carteQuartiers.size() - 1);
            }
        }
        return carteQuartiers.get(carteQuartiers.size() - 1);
    }

    /**
     * Donne le nom de la stratégie utilisée.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String nomStrategie() {
        return "Batisseur";
    }
}
