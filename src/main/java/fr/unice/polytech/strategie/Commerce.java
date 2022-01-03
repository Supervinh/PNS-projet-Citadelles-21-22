package fr.unice.polytech.strategie;

import fr.unice.polytech.cartes.CartePersonnage;
import fr.unice.polytech.cartes.CarteQuartier;
import fr.unice.polytech.Joueur;
import fr.unice.polytech.MoteurDeJeu;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La stratégie qui construit au plus vite ses quartiers pour essayer de finir premier.
 */
public class Commerce implements IStrategie {

    @Override
    public CartePersonnage choixDePersonnage(Joueur joueur, ArrayList<CartePersonnage> personnages) {
        CartePersonnage choix = null;
        int quartierCommerce = 0;
        for (int i = 0; i < joueur.getQuartiersConstruits().size(); i++) {
            if (joueur.getQuartiersConstruits().get(i).getGemme().equals("Commerce et Artisanat")) quartierCommerce++;
        }
        int orMax = MoteurDeJeu.joueurs.stream().filter(j -> j != joueur).mapToInt(Joueur::getOr).max().orElse(0);
        if (orMax > 4)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Voleur")).findAny().orElse(null);
        if (joueur.getQuartiers().size() == 0 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Magicien")).findAny().orElse(null);
        if (joueur.getQuartiers().size() > 2 && joueur.getOr() > 5 && joueur.getQuartiersConstruits().size()>4 && !joueur.isRoi() && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Roi")).findAny().orElse(null);
        if (joueur.getQuartiers().size() > 2 && joueur.getOr() > 5 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Architecte")).findAny().orElse(null);
        if (quartierCommerce > 1 && choix == null)
            choix = personnages.stream().filter(cp -> cp.getNom().equals("Marchand")).findAny().orElse(null);
        if (choix == null) choix = IStrategie.super.choixDePersonnage(joueur, personnages);
        return choix;
    }

    /**
     * On cible le personnage avec le plus de points sinon c'est aléatoire.
     *
     * @param joueur Le joueur qui joue.
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
     * Choisit un quartier qui est le moins cher possible.
     *
     * @param joueur    Le joueur qui joue.
     * @param quartiers La liste des quartiers.
     * @return La carte quartier choisie.
     */
    @Override
    public CarteQuartier choixDeQuartier(Joueur joueur, ArrayList<CarteQuartier> quartiers) {
        ArrayList<CarteQuartier> carteQuartiers = new ArrayList<>(quartiers);
        Collections.sort(carteQuartiers);
        for (CarteQuartier q:carteQuartiers){
            if (q.getGemme().equals("Commerce et Artisanat")){
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
        return "Stratégie se concentrant sur les quartiers 'Commerce et Artisanat'";
    }
}
