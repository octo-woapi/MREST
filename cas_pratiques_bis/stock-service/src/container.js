import { StockRepository } from './stocks/infrastructure/stock.repository.js'
import {getStocksUsecaseFactory} from './stocks/domain/get-stocks.usecase.js'
import {removeFromStockUsecaseFactory} from './stocks/domain/remove-from-stock.usecase.js'
import {addToStockUsecaseFactory} from './stocks/domain/add-to-stock.usecase.js'
import {removeFromStockAndSendEventUsecaseFactory} from './stocks/domain/remove-from-stock-and-send-event.usecase.js'
import broker from './kafka.js'
const messageProducer = broker.producer
const stockRepository = new StockRepository()
const addToStock = addToStockUsecaseFactory(stockRepository, messageProducer)
const removeFromStock = removeFromStockUsecaseFactory(stockRepository, messageProducer)
const removeFromStockAndSendEvent = removeFromStockAndSendEventUsecaseFactory(stockRepository, messageProducer)
const getStocks = getStocksUsecaseFactory(stockRepository)


export default {
    AddtoStock: addToStock,
    RemoveFromStock: removeFromStock,
    RemoveFromStockAndSendEvent: removeFromStockAndSendEvent,
    GetStocks: getStocks,
}
