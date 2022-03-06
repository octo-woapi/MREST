import cors from 'cors'
import express from 'express'
import boom from 'express-boom'
import logger from 'express-pino-logger'

import bodyParser from 'body-parser'
import swaggerJsdoc from 'swagger-jsdoc'
import swaggerUi from 'swagger-ui-express'

import config from './config.js'
import paymentsRoutes from './routes.js'

const api_url = `http://localhost:${config.get('APP_PORT')}`

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'API Payment Service',
            version: '0.1',
            description: 'Une API pour effectuer des paiements',
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
    apis: ['./src/payments/api/v0/payments.api.js'],
}
const specs = swaggerJsdoc(options)

const app = express()

function init () {
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

    paymentsRoutes.use('/api-docs', swaggerUi.serve)
    paymentsRoutes.get('/api-docs', swaggerUi.setup(specs))

    app.use(paymentsRoutes)
    app.use(function(req,res){
        res.boom.notFound(`Could not find resource ${req.path}`)
    })

    app.listen(config.get('APP_PORT'), () => {
        console.log(`Express server listening on ${api_url}`)
    })
}

init()
