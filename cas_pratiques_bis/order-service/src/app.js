import esMain from 'es-main'
import express from 'express'
import boom from 'express-boom'
import config from './config.js'
import logger from 'express-pino-logger'
import cors from 'cors'
import messageBroker from './kafka.js'
import {messagesConsumer, routes} from './interfaces.js'

const app = express()

const TOPIC_WELCOME = 'topic-welcome'

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
    app.use(function(req,res){
        res.boom.notFound(`Could not find resource ${req.path}`)
    })

    app.listen(config.get('APP_PORT'), () => {
        console.log(`Express server listening on http://localhost:${config.get('APP_PORT')}`)
    })
}

async function initTopic() {
    await produceWelcomeMessage()
}

async function produceWelcomeMessage() {
    await messageBroker.producer.connect()
    await messageBroker.producer.send({
        topic: TOPIC_WELCOME,
        messages: [
            {key: '01', value: 'Welcome to the ORDER Service'},
        ],
    })
}

async function init() {
    consume().catch(console.error)
    listen()
    await initTopic()
}

if (esMain(import.meta)) {
    // Module run directly.
    init().catch(
        (err) => {
            console.error(err)
            process.exit(1)
        }
    )
}
