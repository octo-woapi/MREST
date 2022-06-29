import {Status} from './status.js'

export function faireUnTrucUsecaseFactory (httpClient, messageProducer, orderRepository) {
    // Traitement effectué à la réception d'une requête via l'api ou à la consommation d'un message par le consumer
    return async (payload) => {
        // produire 'truc-created' sur le topic 'order' avec pour payload : { objectId: "1234", name: "machin" }
        // messageProducer.send({
        //     topic: 'order',
        //     messages: [
        //         {key: 'truc-created', value: '{"orderId": "1234", "clientId": "machin"}'}
        //     ]
        // })

        // lancer une requête GET
        // httpClient.get('/user?ID=12345')

        // lancer une requête POST
        // httpClient({
        //     method: 'post',
        //     url: '/login',
        //     data: {
        //         firstName: 'Finn',
        //         lastName: 'Williams'
        //     }
        // })

        // e.g. repository
        const orderId = orderRepository.add({itemId: 'bonbon', quantity: 23, status: Status.CANCELED})
        const order = orderRepository.get(orderId)
        order.itemId = 'superBonbon'
        orderRepository.update(order)
        orderRepository.remove(orderId)
    }
}
