import {StockNotFoundError} from './stocks.errors.js'

export function updateStockUsecaseFactory (stockRepository) {
    return async (id, units) => {
        const stock = await stockRepository.get(id)
        if (stock) {
            return await stockRepository.update(id, units)
        }
        throw new StockNotFoundError(id)
    }
}
