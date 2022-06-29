import { getOrdersUsecaseFactory } from './orders/domain/get-orders.usecase.js'
import { getOrderUsecaseFactory } from './orders/domain/get-order.usecase.js'
import { createOrderUsecaseFactory } from './orders/domain/create-order.usecase.js'
import { deleteOrderUsecaseFactory } from './orders/domain/delete-order.usecase.js'
import {validateOrderUsecaseFactory} from './orders/domain/validate-order.usecase.js'
import {OrderRepository} from './orders/infrastructure/order.repository.js'
import broker from './kafka.js'
import axios from 'axios'

import {faireUnTrucUsecaseFactory} from './orders/domain/faire-un-truc.usecase.js'

// producteur de message pour envoyer des évènements dans le broker
const messageProducer = broker.producer

// client http pour effectuer des requêtes
const httpClient = axios

// repository order
const orderRepository = new OrderRepository()

const getOrders = getOrdersUsecaseFactory(orderRepository)
const getOrder = getOrderUsecaseFactory(orderRepository)
const createOrder = createOrderUsecaseFactory(orderRepository)
const validateOrder = validateOrderUsecaseFactory(orderRepository)
const deleteOrder = deleteOrderUsecaseFactory(orderRepository)

// Par exemple ici on passe le httpClient, le messageProducer et le orderRepository à la création du usecase faireUnTruc
// ces objets seront accessibles lors du traitement décrit dans scr/orders/domain/faire-un-truc.usecase.js
const faireUnTruc = faireUnTrucUsecaseFactory(httpClient, messageProducer, orderRepository)

export default {
    GetOrders: getOrders,
    GetOrder: getOrder,
    CreateOrder: createOrder,
    ValidateOrder: validateOrder,
    DeleteOrder: deleteOrder,
    // On n'oublie pas d'ajouter la propriété FaireUnTruc et la méthode de type usecase correspondante
    // afin qu'elle soit accessible via l'objet container disponible dans orders.api.js et orders.consumer.js
    FaireUnTruc: faireUnTruc
}
