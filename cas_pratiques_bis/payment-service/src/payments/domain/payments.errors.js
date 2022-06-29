export class PaymentNotFoundError extends Error {
    constructor (candyId) {
        const message = `Could not find candy with id : ${candyId}`
        super(message)
        this.name = 'PaymentNotFoundError'
    }
}

export class InsufficientBalanceError extends Error {
    constructor (accountNumber) {
        const message = `Unable to process payment not sufficient funds on account ${accountNumber}`
        super(message)
        this.name = 'InsufficientBalanceError'
    }
}

export class AccountNoFoundError extends Error {
    constructor (accountNumber) {
        const message = `Could not find account ${accountNumber}`
        super(message)
        this.name = 'AccountNoFoundError'
    }
}
