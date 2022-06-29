import express from 'express'
const router = express.Router()
import container from './container.js'
import ordersEndpoints from './orders/api/v0/orders.api.js'
import ordersHandlers from './orders/consumer/orders.consumer.js'

export const routes = ordersEndpoints(router, container)
export const messagesConsumer = ordersHandlers(container)