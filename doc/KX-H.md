# Citadelle - Group H

* Rétrospective du déroulé du projet:


* Knowledge Exchange:

* Comment est faite l'architecture du projet ? Et quels choix vous ont amené à la réaliser ainsi ? Où trouver les infos dans la doc ?

Nos cartes quartiers et personnages sont stockées dans des fichiers Excel.
Nous avons aussi une banque de données de prénoms pour les joueurs qui est contenue dans un fichier Excel.
Ces fichiers sont dans le dossier data.
Nos classes sont réparties dans différents package du dossier src/main/java/fr/unice/polytech : 
  - le package couleur pour l'affichage dans le terminal
  - le package piocher pour les stratégies liées à la pioche d'or et des quartiers
  - le package pouvoirs pour les différents pouvoirs des personnages
  - le package stratégie pour les différentes stratégies de jeu
  - le reste (les classes main, moteur de jeu, deck ... )  est dans le dossier src/main/java/fr/unice/polytech sans package particulier



* Qui est responsable de quoi / qui a fait quoi ?

* Quel est le process de l'équipe (comment git est utilisé, ...)

* Avancement sur les fonctionnalités (quelles slices sont faites ? lesquelles restent à faire ?)

* Etat de la base de code : quelles parties sont bien faites ? Quelles parties sont à refactor et pourquoi ?
