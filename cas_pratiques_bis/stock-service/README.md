# stock-service

## Prérequis

### Outils

Installer les outils suivants :

* [Git](https://git-scm.com/book/fr/v2/D%C3%A9marrage-rapide-Installation-de-Git)
* [Node.js](https://nodejs.org/fr/download/package-manager/)
* [Yarn](https://yarnpkg.com/getting-started/install)

### Variables d'environnement

Créer un fichier `.env` à la racine du projet. Vous pouvez prendre exemple sur `.env.example`.

## Installation

```bash
yarn install --frozen-lockfile
```

## Lancement

```bash
yarn start
```

## API

#### POST /v0/stocks/{stockId}/removal

request body

```
{
    "units": 10
}
```

204

#### POST /v0/stocks/{stockId}/addition

request body

```
{
    "units": 12
}
```

204

#### GET /v0/stocks

200

```
[
    {
        "id": "1",
        "units": 10000
    },
    {
        "id": "2",
        "units": 644
    }
]
```