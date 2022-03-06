import { body, validationResult } from 'express-validator'
import { OrderProcessFailedError } from '../../domain/order-process.errors.js'

export default function (router, container) {

    /**
     * @swagger
     *  tags:
     *    name: Orchestrator
     *    description: La gestion des services
     */

    /**
     * @swagger
     * /v0/order-process/:
     *   post:
     *     summary: Création de la gestion d'un Order
     *     tags: [Orchestrator]
     *     requestBody:
     *       required: true
     *       content:
     *         application/json:
     *           schema:
     *             type: object
     *             properties:
     *               order_id:
     *                 type: integer
     *               item_id:
     *                 type: string
     *               quantity:
     *                 type: integer
     *             required:
     *               - order_id
     *               - item_id
     *               - quantity
     *           example:
     *             order_id: 1
     *             item_id: 2
     *             quantity: 10
     *     responses:
     *       200:
     *         description: La modification a été effectuée
     *         content:
     *           application/json:
     *             schema:
     *               type: object
     *               properties:
     *                 order_id:
     *                   type: integer
     *                   description: ID de l'order
     *                 item_id:
     *                   type: string
     *                   description: ID de l'item commandé
     *                 quantity:
     *                   type: integer
     *                   description: Nombre d'items commandé
     *             example:
     *               order_id: 1
     *               item_id: 2
     *               quantity: 10
     *       500:
     *         description: Des erreurs sont survenues
     */
    router.post('/order-process/',
        body('order_id').trim().notEmpty().withMessage('order_id property must be provided'),
        body('item_id').trim().notEmpty().withMessage('item_id property must be provided'),
        body('quantity').trim().notEmpty().withMessage('quantity property must be provided'),
        body('quantity').isNumeric().withMessage('quantity property must be an integer'),
        async function (req, res) {
            validateRequestData(req, res)
            try {
                res.status(200).send(await container.ProcessOrder(
                    parseInt(req.body.order_id),
                    req.body.item_id,
                    parseInt(req.body.quantity)
                ))
            } catch (error) {
                switch (true) {
                case error instanceof OrderProcessFailedError:
                    return res.boom.badData(error.message)
                default:
                    res.boom.internal(error)
                }
            }
        })

    return router
}

const validateRequestData = (req, res) => {
    const errors = validationResult(req)
    if (!errors.isEmpty()) {
        const messages = errors.array().map(element => element.msg)
        return res.boom.badRequest(messages)
    }
}
