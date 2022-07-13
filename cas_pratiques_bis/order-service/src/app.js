import express from 'express'
const app = express()
import boom from 'express-boom'
import config from './config.js'
import logger from 'express-pino-logger'
import cors from 'cors'

import swaggerUi from 'swagger-ui-express'
import swaggerDocument from '../swagger.json' assert { type: 'json' };


import messageBroker from './kafka.js'
import {messagesConsumer, routes} from './interfaces.js'

const consume = async () => {
    await messageBroker.consumer.run({eachMessage: messagesConsumer})
}

function listen () {
    app.use(cors())
    app.use(logger())
    app.use(express.urlencoded({ extended: true }))
    app.use(express.json())



    app.use(boom())
    app.use(routes)
    app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

    app.use(function(req,res){
        res.boom.notFound(`Could not find resource ${req.path}`)
    })

    app.listen(config.get('APP_PORT'), () => {
        console.log(`Express server listening on http://localhost:${config.get('APP_PORT')}`)
    })


}

function init() {
    consume().catch(console.error)
    listen()
}


init()
