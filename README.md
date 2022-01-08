# projet2-ps5-21-22-ps5-21-22-projet2-h

<br/>

### 1 - Fonctionnalités réalisées sur tout le jeu

Notre jeu Citadelles se repose sur les règles de la première édition du jeu physique. Il peut se jouer entre 4 et 7
joueurs. Nous avons implémenté les différents pouvoirs des 8 personnages ainsi que des 10 quartiers spéciaux. Les 8
personnages du jeu sont : l'Assassin, le Voleur, le Magicien, le Roi, l'Évêque, le Marchand, l'Architecte et le
Condottière.

Chaque début de tour commence par la pioche d'une carte personnage. Les huit cartes de personnages sont mélangées, on
pose entre 0 et 2 cartes visibles, la carte du Roi ne doit pas être l'une d'elles sinon elle est remise dans la pioche,
puis une carte cachée qui peut être le Roi. Ensuite, on commence par le joueur qui était roi au dernier tour puis on va
vers sa gauche, donc dans l'ordre croissant des identifiants des bots. Chaque joueur choisit à tour de rôle un
personnage en fonction de la stratégie qu'il utilise. Le restant des cartes sont mises face cachée. Le joueur ayant
pioché l'assassin commence et on continue dans l'ordre des personnages cités précédemment. Il a le choix entre piocher 2
pièces d'or ou piocher 2 cartes quartiers et en choisir une. Une fois son choix fait, il peut construire des quartiers
s'il a le nombre de pièces d'or qu'il lui faut.

Finalement, le pouvoir du personnage est utilisé. On continue ainsi de suite jusqu'à ce qu'au moins un joueur ait
construit 8 quartiers, le jeu s'arrête alors et on déclare un gagnant.

Puisqu'il n'y a que 30 pièces dans le jeu, nous avons un système de banque qui vérifie qu'il y a toujours ces 30
pièces en jeu. On s'assure donc que personne n'a perdu de pièces en cours de route.

Pour faciliter la compréhension du jeu, nous avons développé une interface très colorée et détaillée.

Pour plus de détails sur le fonctionnement du jeu, veuillez vous référer au site que nous avons
utilisé : http://jeuxstrategie.free.fr/Citadelles_complet.php.

<br/>

### 2 - Les logs utilisés

Nous avons décidé d'avoir 7 niveaux de logs :

- Le mode Off où aucune information ne s'affiche
- Le mode Info où on affiche des tableaux de statistiques pour une partie avec le meilleur bot contre d'autres et le
  meilleur bot contre lui-même
- Le mode Fine qui affiche le titre du Jeu
- Le mode Finer qui affiche peu de détails de la partie, mais juste assez pour comprendre ce que le bot est en train de
  faire
- Le mode Finest avec tous les détails possibles d'une partie, on observe la partie du point de vue du bot en
  connaissant tout ce qu'il fait et les décisions qu'il prend
- Le mode Warning qui s'affiche si notamment le fichier CSV est manquant et qu'il a fallu en créer un.
- Le mode Severe qui s'affiche pendant un état critique du programme. <br/>

Nous avons choisi de mettre toutes les méthodes d'affichage dans une seule classe.

<br/>

### 3 - Ce qui a été fait pour les statistiques en CSV

Si le fichier existe déjà on récupère les données préexistantes et on fait les calculs avec ces dernières. On efface les
données du fichier parce que la bibliothèque openCSV ne nous permettait pas de modifier une ligne en particulier, nous
n'avons pas trouvé d'autres solutions que d'effacer le CSV pour réécrire dedans. L'idée de départ était de pouvoir
sélectionner une seule ligne pour la modifier, mais avec un peu de recherches nous nous sommes aperçus qu'on ne pouvait
pas le faire avec un fichier CSV.

Nous avons pris la décision d'avoir trois fichiers CSV qui se créent, un pour la partie avec le meilleur bot contre
d'autres, un autre pour le meilleur bot contre lui-même et le dernier pour nos tests. Ces fichiers sont placés dans le
dossier src/main/resources/save.

<br/>

### 4 - Les bots spécifiques demandés et comparaison avec notre meilleur bot.

Nous avons choisi de séparer les conseils de Richard et d'Alphonse en 3 bots différents, comme le suggérait Alphonse.

Pour le bot bâtisseur : Le but du bâtisseur est de récupérer le plus d'or possible pour pouvoir construire le plus
rapidement possible. Le choix de son personnage va donc dépendre de ce qu'il a construit comme quartiers. Si le joueur a
au moins un quartier Noblesse, il va prendre en priorité le Roi qui rapporte des pièces d'or selon le nombre de
quartiers Noblesse construits. Il va prendre le Marchand s'il a au moins un quartier Commerce et Artisanat de construit.
Si le joueur a construit au moins 2 quartiers et a au moins 5 pièces d'or il prendra Architecte ce qui peut lui
permettre de construire jusqu'à 3 quartiers en un seul tour.

Pour le bot opportuniste : Le but de l'opportuniste est d'attaquer et de se faire attaquer le moins possible par les
autres joueurs. Il va prendre en priorité le Voleur pour récupérer de l'or rapidement. S'il a construit au moins un
quartier Religieux, il prendra l'Évêque. S'il a plus que 1 pièce d'or il va prendre le Condottière pour ralentir un
autre joueur.

Pour le bot agressif : Le but de l'agressif est d'empêcher les autres d'avancer dans le jeu. Il va donc prendre en
priorité l'Assassin pour faire sauter le tour d'une personne. Si le bot voit qu'un autre joueur à plus de 5 quartiers de
construits il va prendre le rôle du Condottière pour pouvoir détruire un quartier. Si le joueur n'a plus de quartiers en
main, il prendra le Magicien pour pouvoir récupérer les cartes de quelqu'un d'autre. Si un joueur a plus de 7 pièces
d'or le joueur va prendre le Voleur pour pouvoir prendre de l'or.

Nous avons deux meilleurs bots qui s'appellent Commerçant et Rusher, ils ont tous les deux comme but de construire le
plus vite possible des quartiers pour essayer de finir en premier et remporter la partie. La seule différence entre ses
deux bots est dans le choix de son personnage et le choix du quartier à construire. Le bot Rusher va choisir en priorité
l'Architecte et s'il a moins de 3 pièces d'or il va prendre le Voleur. Le choix du personnage du bot Commerçant est
beaucoup plus poussé et repose sur ce qu'il a dans sa main au moment donné. Pour le choix du quartier à construire, le
Commerçant va favoriser le quartier le moins cher avec la gemme Commerce et Artisanat, alors que le bot Rusher favorise
tout simplement son quartier le moins cher.

Ces bots ont tendance à gagner parce qu'ils construisent assez rapidement et ne laissent pas le temps aux autres de
construire leurs quartiers. 

Ayant un taux de victoire quasiment identique, nous avons fait le choix de désigner le bot Rusher comme étant notre 
meilleur bot, car il gagne dans une majorité des cas (même si Commerçant a ses chances de gagner aussi). Bien que notre bot Rusher se repose principalement sur de l'aléatoire, nous pouvons remarquer que cela est suffisamment efficace puisqu'il reste plus performant que les autres bots.
