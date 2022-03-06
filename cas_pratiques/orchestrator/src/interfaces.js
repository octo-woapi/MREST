import express from 'express'

import orchestratorEndpoints from './orchestrator/api/v0/orchestrator.api.js'
import orchestratorHandlers from './orchestrator/consumer/orchestrator.consumer.js'
import container from './container.js'

const router = express.Router()

export const routes = orchestratorEndpoints(router, container)
export const messagesHandler = orchestratorHandlers(container)
