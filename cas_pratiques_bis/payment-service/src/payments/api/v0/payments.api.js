import {param, body, validationResult} from 'express-validator'
import {AccountNoFoundError, InsufficientBalanceError} from '../../domain/payments.errors.js'

export default function (router, container) {
    router.use(async function (
        req,
        res,
        next
    ) {
        console.log('Time: %d', Date.now())
        next()
    })

    router.get('/v0/balances/:account_number',
        param('account_number').trim().notEmpty().withMessage('account number must be provided'),
        async function (
            req,
            res,
        ) {
            try {
                res.status(200).send(await container.GetBalance(req.params.account_number))
            } catch (error) {
                switch (true) {
                case error instanceof AccountNoFoundError:
                    return res.boom.notFound(error.message)
                default:
                    res.boom.internal(error)
                }
            }
        })

    router.post('/v0/payments/',
        body('client_id').trim().notEmpty().withMessage('client_id must be provided'),
        body('client_id').trim().isString().withMessage('client_id must be a string'),
        body('item_id').trim().notEmpty().withMessage('item_id must be provided'),
        body('item_id').trim().isString().withMessage('item_id must be a string'),
        body('quantity').trim().notEmpty().withMessage('quantity must be provided'),
        body('quantity').isNumeric().withMessage('quantity must be an integer'),
        async function (req, res) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) {
                const messages = errors.array().map(element => element.msg)
                return res.boom.badRequest(messages)
            }

            try {
                await container.CreatePayment(
                    req.body.client_id,
                    req.body.item_id,
                    parseInt(req.body.quantity)
                )
                res.status(201).send()
            } catch (error) {
                switch (true) {
                case error instanceof AccountNoFoundError:
                    return res.boom.notFound(error.message)
                case error instanceof InsufficientBalanceError:
                    return res.boom.badData(error.message)
                default:
                    res.boom.internal(error)
                }
            }
        })

    return router
}

