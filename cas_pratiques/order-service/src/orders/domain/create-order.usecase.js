import { Status } from './status.js'

export function createOrderUsecaseFactory (orderRepository, messageProducer) {
    return async (clientId, itemId, quantity) => {
        const orderId = await orderRepository.add({
            clientId,
            itemId,
            quantity,
            status: Status.PENDING
        })
        return await orderRepository.get(orderId)
    }
}
