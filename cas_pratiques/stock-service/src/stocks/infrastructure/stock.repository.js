const stocks = [
    { id: 1, units: 231 },
    { id: 2, units: 644 },
    { id: 3, units: 89 },
    { id: 4, units: 964 },
    { id: 5, units: 142 },
    { id: 6, units: 354 }
]

export class StockRepository {
    async get(stockId = undefined) {
        if (stockId) {
            return stocks.find(stock => stock.id === stockId)
        }
        return stocks
    }

    async update(id, units) {
        const index = this.getStockIndex(id)
        if (index >= 0) {
            stocks[index].units = units
        }
    }

    getStockIndex(stockId) {
        return stocks.findIndex((stock => stock.id === stockId))
    }
}
