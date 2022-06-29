# payment-service

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

### Account number list

- R4CL0
- R4CH0
- C4SSO
- CL0D0
- D34L0
- PR010

#### GET /v0/balances/{accountNumber}

200

```
{
    "number": "CL0D0",
    "balance": 100
}
```

#### POST /v0/payments

request body

```
{
    "debit_account_number": "CL0D0",
    "credit_account_number": "D34L0",
    "amount": 100
}
```

201
