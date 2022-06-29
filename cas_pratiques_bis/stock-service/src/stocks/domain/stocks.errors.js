export class StockNotFoundError extends Error {
    constructor (stockId) {
        const message = `Could not find stock with id : ${stockId}`
        super(message)
        this.name = 'StockNotFoundError'
    }
}

export class InsufficientStockError extends Error {
    constructor (stockId) {
        const message = `Insufficient ${stockId} stock`
        super(message)
        this.name = 'InsufficientStockError'
    }
}
