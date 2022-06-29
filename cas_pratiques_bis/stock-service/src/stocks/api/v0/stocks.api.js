import {param, body, validationResult} from 'express-validator'
import {InsufficientStockError, StockNotFoundError} from '../../domain/stocks.errors.js'

export default function (router, container) {
    router.use(async function (
        req,
        res,
        next
    ) {
        console.log('Time: %d', Date.now())
        next()
    })

    router.get('/v0/stocks',
        async function (
            req,
            res,
        ) {
            try {
                res.status(200).send(await container.GetStocks())
            } catch (error) {
                res.boom.internal(error)
            }
        })

    router.post('/v0/stocks/:id/removal',
        param('id').trim().notEmpty().withMessage('stock\'s id must be provided'),
        body('units').trim().notEmpty().withMessage('units property must be provided'),
        body('units').isNumeric().withMessage('units property value must be an integer'),
        async function (req, res) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) {
                const messages = errors.array().map(element => element.msg)
                return res.boom.badRequest(messages)
            }

            try {
                await container.RemoveFromStock(req.params.id, parseInt(req.body.units))
                res.status(204).send()
            } catch (error) {
                switch (true) {
                case error instanceof StockNotFoundError:
                    return res.boom.notFound(error.message)
                case error instanceof InsufficientStockError:
                    return res.boom.notFound(error.message)
                default:
                    res.boom.internal(error)
                }
            }
        })

    router.post('/v0/stocks/:id/addition',
        param('id').trim().notEmpty().withMessage('stock\'s id must be provided'),
        body('units').trim().notEmpty().withMessage('units property must be provided'),
        body('units').isNumeric().withMessage('units property value must be an integer'),
        async function (req, res) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) {
                const messages = errors.array().map(element => element.msg)
                return res.boom.badRequest(messages)
            }

            try {
                await container.AddtoStock(req.params.id, parseInt(req.body.units))
                res.status(204).send()
            } catch (error) {
                switch (true) {
                case error instanceof StockNotFoundError:
                    return res.boom.notFound(error.message)
                default:
                    res.boom.internal(error)
                }
            }
        })

    return router
}

