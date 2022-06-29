import {StockNotFoundError} from './stocks.errors.js'

export function addToStockUsecaseFactory (stockRepository) {
    return async (id, unitsToRemove) => {
        const stock = await stockRepository.get(id)
        if (stock) return await stockRepository.update(id, stock.units + unitsToRemove)
        throw new StockNotFoundError(id)
    }
}
