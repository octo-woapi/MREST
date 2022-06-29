import {InsufficientStockError, StockNotFoundError} from './stocks.errors.js'

export function removeFromStockUsecaseFactory (stockRepository) {
    return async (id, unitsToRemove) => {
        const stock = await stockRepository.get(id)
        if (stock.units < unitsToRemove) throw new InsufficientStockError(id)
        if (stock) return await stockRepository.update(id, stock.units - unitsToRemove)
        throw new StockNotFoundError(id)
    }
}
