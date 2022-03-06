export class StockNotFoundError extends Error {
    constructor (stockId) {
        const message = `Could not find stock with id : ${stockId}`
        super(message)
        this.name = 'StockNotFoundError'
    }
}
