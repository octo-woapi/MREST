import cors from 'cors'
import express from 'express'
import boom from 'express-boom'
import logger from 'express-pino-logger'

import bodyParser from 'body-parser'
import swaggerJsdoc from 'swagger-jsdoc'
import swaggerUi from 'swagger-ui-express'

import messageBroker from './kafka.js'
import config from './config.js'
import { messagesHandler, routes } from './interfaces.js'

const api_url = `http://localhost:${config.get('APP_PORT')}`

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'API Orchestrator',
            version: '0.1',
            description: 'Une API Orchestrator pour gérer des services',
            license: {
                name: 'MIT',
                url: 'https://spdx.org/licenses/MIT.html',
            },
            contact: {
                name: 'OCTO Academy',
                url: 'https://www.octo.academy',
                email: 'academy@octo.com',
            },
        },
        servers: [
            {
                url: `${api_url}`
            },
        ],
    },
    apis: ['./src/orchestrator/api/v0/orchestrator.api.js'],
}
const specs = swaggerJsdoc(options)

const app = express()

const consume = async () => {
    await messageBroker.consumer.run({eachMessage: messagesHandler})
}

const listen = () => {
    app.use(cors())
    app.use(logger())
    app.use(express.urlencoded({ extended: true }))
    app.use(express.json())
    app.use(boom())

    app.use(bodyParser.json())
    app.use(
        bodyParser.urlencoded({
            extended: true,
        })
    )

    routes.use('/api-docs', swaggerUi.serve)
    routes.get('/api-docs', swaggerUi.setup(specs))

    app.use(routes)
    app.use(function (req, res) {
        res.boom.notFound(`Could not find resource ${req.path}`)
    })
    app.listen(config.get('APP_PORT'), () => {
        console.log(`Express server listening on ${api_url}`)
    })
}

function init() {
    consume().catch(console.error)
    listen()
}

init()
