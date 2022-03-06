import { param, body, validationResult } from 'express-validator'
import { AccountNoFoundError, InsufficientBalanceError } from '../../domain/payments.errors.js'

export default function (router, container) {

    router.use(async function (
        req,
        res,
        next
    ) {
        console.log('Time: %d', Date.now())
        next()
    })

    /**
     * @swagger
     *  tags:
     *    name: Payments
     *    description: Les Paiements
     */

    /**
     * @swagger
     * components:
     *   schemas:
     *     Account:
     *       type: object
     *       required:
     *         - number
     *         - balance
     *       properties:
     *         number:
     *           type: string
     *         balance:
     *           type: integer
     *       example:
     *         number: 'R4CL0'
     *         balance: 5000
     */

    /**
     * @swagger
     * /v0/balances/{account_number}:
     *   get:
     *     summary: Renvoie le solde du compte d'après son numéro
     *     tags: [Payments]
     *     parameters:
     *       - in : path
     *         name: account_number
     *         description: numéro de compte
     *         schema:
     *           type: string
     *         required: true
     *     responses:
     *       200:
     *         content:
     *           application/json:
     *             schema:
     *               $ref: '#/components/schemas/Account'
     */
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

    /**
     * @swagger
     * /v0/payments:
     *   post:
     *     summary: Creation d'un paiement d'un compte à un autre
     *     tags: [Payments]
     *     requestBody:
     *       required: true
     *       content:
     *         application/json:
     *           schema:
     *             type: object
     *             properties:
     *               debit_account_number:
     *                 type: string
     *               credit_account_number:
     *                 type: string
     *               amount:
     *                 type: integer
     *             required:
     *               - debit_account_number
     *               - credit_account_number
     *               - amount
     *     responses:
     *       201:
     *         description: Le paiement a été effectué
     *       500:
     *         description: Des erreurs sont survenues
     */
    router.post('/v0/payments/',
        body('debit_account_number').trim().notEmpty().withMessage('debit account number must be provided'),
        body('debit_account_number').trim().isString().withMessage('debit account number must be a string'),
        body('credit_account_number').trim().notEmpty().withMessage('credit account number must be provided'),
        body('credit_account_number').trim().isString().withMessage('credit account number must be a string'),
        body('amount').trim().notEmpty().withMessage('amount must be provided'),
        body('amount').isNumeric().withMessage('amount must be an integer'),
        async function (req, res) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) {
                const messages = errors.array().map(element => element.msg)
                return res.boom.badRequest(messages)
            }

            try {
                await container.CreatePayment(
                    req.body.debit_account_number,
                    req.body.credit_account_number,
                    parseInt(req.body.amount)
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

