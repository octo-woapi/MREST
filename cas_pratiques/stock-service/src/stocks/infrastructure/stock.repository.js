const stocks = [
    {id: 'brow', units: 231},
    {id: 'coke', units: 644 },
    {id: 'acid', units: 89 },
    {id: 'crys', units: 964 },
    {id: 'cake', units: 142 },
    {id: 'bloo', units: 354 }
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
