import { param, body, validationResult } from 'express-validator'

import { StockNotFoundError } from '../../domain/stocks.errors.js'

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
     *    name: Stocks
     *    description: Les Stocks
     */

    /**
     * @swagger
     * components:
     *   schemas:
     *     Stock:
     *       type: object
     *       required:
     *         - id
     *         - units
     *       properties:
     *         id:
     *           type: integer
     *         units:
     *           type: integer
     *       example:
     *         id: 1
     *         units: 200
     */

    /**
     * @swagger
     * /v0/stocks:
     *   get:
     *     summary: Renvoie la liste des stocks
     *     tags: [Stocks]
     *     responses:
     *       200:
     *         content:
     *           application/json:
     *             schema:
     *               type: array
     *               items:
     *                 $ref: '#/components/schemas/Stock'
     */
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

    /**
     * @swagger
     * /v0/stocks/{id}:
     *   put:
     *     summary: Creation d'une confiserie
     *     tags: [Stocks]
     *     parameters:
     *       - in: path
     *         name: id
     *         description: ID du stock
     *         required: true
     *         schema:
     *           type: integer
     *     requestBody:
     *       required: true
     *       content:
     *         application/json:
     *           schema:
     *             type: object
     *             properties:
     *               units:
     *                 type: integer
     *             required:
     *               - units
     *     responses:
     *       204:
     *         description: La modofication a été effectuée
     *       500:
     *         description: Des erreurs sont survenues
     */
    router.put('/v0/stocks/:id',
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
                await container.UpdateStock(req.params.id, parseInt(req.body.units))
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

