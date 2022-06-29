import express from 'express'
const router = express.Router()
import container from './container.js'
import stocksEndpoints from './stocks/api/v0/stocks.api.js'
import stocksHandlers from './stocks/consumer/stocks.consumer.js'

export const routes = stocksEndpoints(router, container)
export const messagesConsumer = stocksHandlers(container)