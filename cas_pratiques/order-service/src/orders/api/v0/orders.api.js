import { param, body, validationResult } from 'express-validator'

import { OrderNotFoundError } from '../../domain/orders.errors.js'

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
     *    name: Orders
     *    description: Les Orders
     */

    /**
     * @swagger
     * components:
     *   schemas:
     *     Status:
     *       type: string
     *       enum:
     *         - pending
     *         - validated
     *         - canceled
     */

    /**
     * @swagger
     * components:
     *   schemas:
     *     Order:
     *       type: object
     *       required:
     *         - id
     *         - itemId
     *         - quantity
     *         - status
     *       properties:
     *         id:
     *           type: integer
     *         itemId:
     *           type: string
     *         quantity:
     *           type: integer
     *         status:
     *           $ref: '#/components/schemas/Status'
     *       example:
     *         id: 1
     *         itemId: brow
     *         quantity: 123
     *         status: validated
     */

    /**
     * @swagger
     * /v0/orders:
     *   get:
     *     summary: Renvoie la liste des orders
     *     tags: [Orders]
     *     responses:
     *       200:
     *         content:
     *           application/json:
     *             schema:
     *               type: array
     *               items:
     *                 $ref: '#/components/schemas/Order'
     */
    router.get('/v0/orders',
        async function (
            req,
            res,
        ) {
            try {
                res.status(200).send(await container.GetOrders())
            } catch (error) {
                res.boom.internal(error)
            }
        })

    /**
     * @swagger
     * /v0/orders/{id}:
     *   get:
     *     summary: Renvoie un order d'après son id
     *     tags: [Orders]
     *     parameters:
     *       - in : path
     *         name: id
     *         description: id de l'order
     *         schema:
     *           type: integer
     *         required: true
     *     responses:
     *       200:
     *         content:
     *           application/json:
     *             schema:
     *               $ref: '#/components/schemas/Order'
     */
    router.get('/v0/orders/:id',
        param('id').trim().notEmpty().withMessage('order\'s id must be provided'),
        async function (
            req,
            res,
        ) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) return res.boom.badRequest(errors.array().map(element => element.msg))

            try {
                res.status(200).send(await container.GetOrder(parseInt(req.params.id)))
            } catch (error) {
                switch (true) {
                case error instanceof OrderNotFoundError:
                    return res.boom.notFound(error.message)
                default:
                    res.boom.internal(error)
                }

            }
        })

    /**
     * @swagger
     * /v0/orders:
     *   post:
     *     summary: Creation d'un order
     *     tags: [Orders]
     *     requestBody:
     *       required: true
     *       content:
     *         application/json:
     *           schema:
     *             type: object
     *             properties:
     *               client_id:
     *                 type: string
     *               item_id:
     *                 type: string
     *               quantity:
     *                 type: integer
     *             required:
     *               - clientId
     *               - itemId
     *               - quantity
     *     responses:
     *       201:
     *         description: La création a été effectuée
     *         content:
     *           application/json:
     *             schema:
     *               $ref: '#/components/schemas/Order'
     *       500:
     *         description: Some server error
     */
    router.post('/v0/orders/',
        body('itemId').trim().notEmpty().withMessage('itemId property must be provided'),
        body('itemId').trim().isString().withMessage('itemId property must be a string'),
        body('clientId').trim().notEmpty().withMessage('clientId property must be provided'),
        body('clientId').trim().isString().withMessage('clientId property must be a string'),
        body('quantity').trim().notEmpty().withMessage('quantity property must be provided'),
        body('quantity').trim().isNumeric().withMessage('quantity property must be an integer'),
        async function (req, res) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) return res.boom.badRequest(errors.array().map(element => element.msg))

            try {
                const result = await container.CreateOrder(req.body.clientId, req.body.itemId, req.body.quantity)
                res.status(201).send({
                    id: result.id,
                    clientId: result.clientId,
                    itemId: result.itemId,
                    quantity: parseInt(result.quantity),
                    status: result.status
                })
            } catch (error) {
                res.boom.internal(error)
            }
        })

    /**
     * @swagger
     *  /v0/orders/{id}:
     *    delete:
     *      summary: Supprimer un order selon son id
     *      tags: [Orders]
     *      parameters:
     *        - in: path
     *          name: id
     *          description: ID de l'order
     *          required: true
     *          schema:
     *            type: integer
     *      responses:
     *        204:
     *          description: L'order a été supprimé
     *        404:
     *          description: L'order n'a pas été trouvé
     *
     */
    router.delete('/v0/orders/:id',
        param('id').trim().notEmpty().withMessage('order\'s id must be provided'),
        async function (
            req,
            res,
        ) {
            const errors = validationResult(req)
            if (!errors.isEmpty()) return res.boom.badRequest(errors.array().map(element => element.msg))

            try {
                await container.DeleteOrder(parseInt(req.params.id))
                res.status(204).end()
            } catch (error) {
                switch (true) {
                case error instanceof OrderNotFoundError:
                    return res.boom.notFound(error.message)
                default:
                    res.boom.internal(error)
                }
            }
        })

    return router
}
