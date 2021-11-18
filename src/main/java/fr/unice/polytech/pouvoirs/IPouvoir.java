package fr.unice.polytech.pouvoirs;

import fr.unice.polytech.Joueur;

public interface IPouvoir {
    void utiliserPouvoir(Joueur joueur, Joueur cible);
}
