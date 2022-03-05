import express from 'express'

import container from './container.js'
import ordersEndpoints from './orders/api/v0/orders.api.js'

const router = express.Router()

export default ordersEndpoints(router, container)
