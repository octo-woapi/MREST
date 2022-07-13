# Socle kafka 

## Objectif
Fournir un socle permettant de pratiquer sur les notions de messaging de microservices.

## Contenu et technologie
La stack est composée de : 
- kafka : le broker de message
- kafka UI : GUI permettant de visualiser les topics et messages de kafka
- Zookeeper : composant de gestion de configuration necessaire au fonctionnement de kafka

La stack se lance idéalement sur docker compose >V2

## Requirements
- docker
- docker compose >v2

## Fonctionnement

Le script start.sh positionne la bonne ip d'annonce pour kafka avant son lancement, et lance le docker compose.
Le script fabrique ou patch un .env, utilisé par docker compose pour renseigner ses variables d'environnement.

**L'ip recherchée est une ip en commun avec le système hôte, sur lequel nous allons lancer les microservices. L'ip doit être valide pour que les microservices se connectent.**

il est possible que sur certain système ce script necessite un **sudo**.

````shell
./start.sh
````

