# projet2-ps5-21-22-ps5-21-22-projet2-h

<br/>

### 1 - Un résumé des fonctionnalités réalisées sur tout le jeu (couverture des règles du jeu indépendamment de ce qui était demandé cette semaine),et éventuellement la liste de ce qui n'a pas été fait.

Notre jeu Citadelles se repose sur les règles de la première édition du jeu physique. Il peut se jouer entre 4 et 7 joueurs. Nous avons implémenté les différents pouvoirs des 8 personnages ainsi que des 10 quartiers spéciaux. Les 8 personnages du jeu sont: l'Assassin, le Voleur, le Magicien, le Roi, l'Évêque, le Marchand, l'Architecte et le Condottière. 

Chaque début de tour commence par la pioche d'une carte personnage. Les huit cartes de personnages sont mélangées, on pose entre 0 et 2 cartes visibles, la carte du Roi ne doit pas être l'une d'elle sinon elle est remise dans la pioche, puis une carte cachée qui peut être le Roi. Ensuite, on commence par le joueur qui était roi  au dernier tour puis on va vers sa gauche, donc dans l'ordre croissant des identifiants des bots. Chaque joueur choisit à tour de role un personnage en fonction de  la stratégie qu'il utilise. Le restant des cartes sont mises face cachée. Le joueur ayant pioché l'assassin commence et on continue dans l'ordre des personnages  cité précedement. Il a le choix entre piocher 2 pièces d'or ou piocher 2 cartes quartiers et en choisir une. Une fois son choix fait, il peut construire des quartiers s'il a le nombre de pièces qu'il lui faut.

Finalement, le pouvoir du personnage est utilisé. On continue ainsi de suite jusqu'à ce qu'au moins un joueur ait construit 8 quartiers, le jeu s'arrête alors et on déclare un gagnant. 

Puisqu'il n'y a que 30 pièces dans le jeu, nous avons un système de banque qui vérifie qu'il y ait toujours ces 30 pièces en jeu. On s'assure donc que personne n'a perdu de pièces en cours de route.

Pour faciliter la compréhension du jeu nous avons développé une interface très colorée et détaillée.

Pour plus de détails sur le fonctionnement du jeu, veuillez vous référer au site que nous avons utilisé: http://jeuxstrategie.free.fr/Citadelles_complet.php .

<br/>

### 2 - Un résumé de ce qui a été fait pour les logs (en quelques lignes max, quels choix ont été faits pour les réaliser)

Nous avons décidé d'avoir 6 niveaux de logs:
- Le mode Off où aucune information ne s'affiche
- Le mode Info où on affiche des tableaux de statistiques pour une partie avec le meilleur bot contre d'autres et le meilleur bot contre lui-même
- Le mode Finer qui affiche peu de détails de la partie mais juste assez pour comprendre ce que le bot est entrain de faire
- Le mode Finest avec tous les détails possibles d'une partie, on observe la partie du point de vue du bot en connaissant tout ce qu'il fait et les décisions qu'il prend
- Le mode Warning qui s'affiche si notamment le fichier CSV est manquant et qu'il a fallu en créer un. 
- Le mode Severe qui s'affiche pendant un état critique du programme. <br/>

Nous avons choisi de mettre toutes les méthodes d'affichage dans une seule classe.

<br/>

### 3 - Un résumé de ce qui a été fait pour les statistiques en CSV (en quelques lignes max, quels choix ont été faits pour les réaliser)

Si le fichier existe déja on récupère les données préexistantes et on fait les calculs avec ces dernières. On efface les données parce qu'on les a récupérées, et on écrit dans le CSV. Nous n'avons pas trouvé d'autres solutions que d'effacer le CSV pour réécrire dedans. L'idée de départ était de pouvoir sélectionner une seule ligne pour la modifier mais avec un peu de recherches nous nous sommes aperçus qu'on ne pouvait pas le faire avec un fichier CSV.

Nous avons pris la décision d'avoir deux fichiers CSV qui se créent, un pour la partie avec le meilleur bot contre d'autres et un autre pour le meilleur bot contre lui-même. Ces deux fichiers sont placés dans le dossier src/main/resources/save.

<br/>

### 4 - Un résumé de ce qui a été fait pour le bot spécifique demandé (1 ou 2 bots selon le choix que vous avez fait, pas de problème...), et éventuellement une comparaison avec votre meilleur bot et une analyse de pourquoi celui qui gagne est le meilleur.

Nous avons choisi de séparer les conseils de Richard et d'Alphonse en 3 bots différents, comme le suggérait Alphonse.

Pour le bot batisseur: Le but du batisseur est de récupérer le plus d'or possible pour pouvoir construire le plus rapidement possible. Le choix de son personnage va donc dépendre de ce qu'il a construit comme quartiers. Si le joueur a au moins un quartier noblesse il va prendre en priorité le roi qui rapporte des pièces d'or selon le nombre de quartiers noblesse construits. Il va prendre le marchand si il a au moins un quartier commerce et artisanat de construit. Si le joueur a construit au moins 2 quartiers et a au moins 5 pièces d'or il prendra architecte ce qui peut le permettre de construire jusqu'à 3 quartiers en un seul tour. 

Pour le bot opportuniste: Le but de l'opportuniste est d'attaquer et de se faire attaquer le moins possible par les autres joueurs. Il va prendre en priorité le voleur pour récupérer de l'or vite fait. Si il a construit au moins un quartier religieux il prendra l'évêque. Si il a plus que 1 pièce d'or il va prendre le condottière pour ralentir un autre joueur.

Pour le bot agressif: Le but de l'agressif est d'empêcher les autres d'avancer dans le jeu. Il va donc prendre en priorité l'assassin pour faire sauter le tour d'une personne. Si le bot voit qu'un autre joueur à plus de 5 quartiers de construits il va prendre le rôle du condottière pour pouvoir détruire un quartier. Si le joueur n'a plus de quartiers en main il prendra le magicien pour pouvoir récupérer les cartes de quelqu'un d'autre. Si un joueur a plus de 7 pièces d'or le joueur va prendre le voleur pour pouvoir prendre de l'or. 

Notre meilleur bot s'appelle Commerce, son but est de construire le plus vite possible ces quartiers pour essayer de finir en premier et remporter la partie.
