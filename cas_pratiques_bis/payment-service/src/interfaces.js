import express from 'express'
const router = express.Router()
import container from './container.js'
import paymentsEndpoints from './payments/api/v0/payments.api.js'
import paymentsHandlers from './payments/consumer/payments.consumer.js'

export const routes = paymentsEndpoints(router, container)
export const messagesConsumer = paymentsHandlers(container)