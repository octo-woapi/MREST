import express from 'express'

import container from './container.js'
import stocksEndpoints from './stocks/api/v0/stocks.api.js'

const router = express.Router()

export default stocksEndpoints(router, container)
