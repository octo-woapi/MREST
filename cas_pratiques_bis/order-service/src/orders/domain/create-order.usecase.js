import {Status} from './status.js'

export function createOrderUsecaseFactory (orderRepository) {
    return async (clientId, itemId, quantity) => {
        const orderId = await orderRepository.add({itemId: itemId, quantity: quantity, status: Status.VALIDATED})
        return await orderRepository.get(orderId)
    }
}
