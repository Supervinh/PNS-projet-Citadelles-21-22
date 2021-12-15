# Citadelle - Group H

* Rétrospective du déroulé du projet:


* Knowledge Exchange:

* Comment est faite l'architecture du projet ? Et quels choix vous ont amené à la réaliser ainsi ?

Nos cartes quartiers et personnages sont stockées dans des fichiers Excel.
Nous avons aussi une banque de données de prénoms pour les joueurs qui est contenue dans un fichier Excel.
Ces fichiers sont dans le dossier data.
Nos classes sont réparties dans différents package du dossier src/main/java/fr/unice/polytech : 
  - le package couleur pour l'affichage dans le terminal
  - le package piocher pour les stratégies liées à la pioche d'or et des quartiers
  - le package pouvoirs pour les différents pouvoirs des personnages
  - le package stratégie pour les différentes stratégies de jeu
  - le reste (les classes main, moteur de jeu, deck ... )  est dans le dossier src/main/java/fr/unice/polytech sans package particulier

Ces choix ont été faits parce que c'est ce qui nous a paru le plus logique et ordonné. Celà nous a permis de ne pas nous perdre dans nos nombreuses classes.

* Où trouver les infos dans la doc ?

Nous avons utilisé les règles du jeu trouvées sur ce site : http://jeuxstrategie.free.fr/Citadelles_complet.php

* Qui est responsable de quoi / qui a fait quoi ?

Matis a fait le visuel du projet, les différentes stratégies, la banque et le deck de carte.
Vinh s'est occupé de la majorité des tests.
Aurélia a codé le début de tour où l'on écarte certaines cartes face visible et face cachée, et la gestion du cas d'égalité entre 2 joueurs.
Clervie a rédigé la javaDoc.
Les pouvoirs des personnages ont été répartis entre nous, chaque membre du groupe s'est occupé de 2 personnages.
Les pouvoirs des cartes ont été faits par Clervie et Aurélia.

* Quel est le process de l'équipe (comment git est utilisé, ...)

A chaque début de cours, nous avons fait un point sur ce qu'on avait fait, ce qu'on allait faire et nos difficultés pour ce qui nous restait à faire.
Nous avons utilisé git comme demandé, en créant différentes issues, différentes milestones et en suivant l'avancée sur le Kanban.
Au départ, les commits liées au refactor n'étaient pas liées à une issue.

* Avancement sur les fonctionnalités (quelles slices sont faites ? lesquelles restent à faire ?)




* Etat de la base de code : quelles parties sont bien faites ? Quelles parties sont à refactor et pourquoi ?
