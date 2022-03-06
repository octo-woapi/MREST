import express from 'express'

import container from './container.js'
import paymentsEndpoints from './payments/api/v0/payments.api.js'

const router = express.Router()

export default paymentsEndpoints(router, container)
