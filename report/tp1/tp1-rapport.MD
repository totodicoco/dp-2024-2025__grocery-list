# TP de Tom HUGUET, Toby POLLOCK, Maxence MARTIN | L3 SDN

# My grocery list

## Que fait notre application ?

Ajouter un article à la liste : utilisez la commande add pour ajouter un article avec une quantité.

Lister les articles : utilisez la commande list pour afficher tous les articles et leurs quantités dans la liste de courses.

Retirer un article de la liste : utilisez la commande remove pour supprimer un article spécifique de la liste.

Vider la liste : utilisez la commande clear pour effacer tous les articles de la liste de courses.

Sauvegarde en format JSON ou CSV. Pour préciser il faut utiliser la commande --format

Validation des arguments : gère les erreurs de format ou de syntaxe dans les arguments de ligne de commande

## Point d'amélioration

Le code original avait quelques lacunes.

Pour l'ajout d'éléments à la liste, des doublons pouvaient apparaitre ce qui n'est pas très logique. Ainsi, si dans la liste il y a déjà 10 MILK et que l'on ajoute 10 MILK, alors la quantité va s'additionner pour donner 20 MILK.

Pour la suppression d'éléments, si l'on essayait de supprimer un élément contenant le noms d'un autre élément, alors cela supprimait les deux. Par exemple, la liste contient MILK et MILKSHAKE, alors avant la mise à jour du code si on choisissait de supprimer MILK, MILKSHAKE était aussi supprimé. De plus il n'est pas possible de supprimer un élément qui n'est pas dans la liste.

Nous avons rajouté la fonctionnalité de pouvoir supprimer toute la liste avec la commande clear. Cela fonctionne de la même manière que list.

Nous avons aussi le choix de stocker notre liste en format CSV ou JSON . Pour cela il faut utiliser la commande --format quand on réalise une action sur les listes.

## les tests unitaires

Il n'y a pas d'erreur dans les tests unitaires et les noms des tests expliquent concrètrement ce qui est testé.

## Avis global sur le TP

Globalement le sujet est plutôt clair et facile à comprendre. Nous avons rencontrés quelques difficultés avec les tests unitaires qui étaient plus complexes que ceux du premier TP. Le seul autre point compliqué était la mise en place du projet avec Maven et notre version de Java. Sinon le reste était plutôt accessible.
