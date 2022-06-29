# MREST

## 💻 Installation de kafka en local

Aller dans le dossier `kafka-cluster-compose` et lancer la commande suivante :
```
docker compose up -d
```

Trois containers devraient démarrer en fond. Vous pouvez les voir via la commande suivante : 
```
docker ps
...
CONTAINER ID   IMAGE                           COMMAND                  CREATED         STATUS         PORTS                                                                     NAMES
d37356b92228   provectuslabs/kafka-ui:latest   "/bin/sh -c 'java $J…"   9 seconds ago   Up 5 seconds   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp                                 kafka-cluster-compose-kafka-ui-1
2dd4ae643153   bitnami/kafka:latest            "/opt/bitnami/script…"   9 seconds ago   Up 6 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp                                 kafka-cluster-compose-kafka-1
c4dc82dd9114   bitnami/zookeeper:latest        "/opt/bitnami/script…"   9 seconds ago   Up 7 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 8080/tcp   kafka-cluster-compose-zookeeper-1
```

> 💡 L'interface `kafka-ui` est disponible via l'url: [localhost:8080](http://localhost:8080)
