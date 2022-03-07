export function createProcessOrderUsecaseFactory () {
    return async (clientId, orderId, itemId, quantity) => {
        return { clientId, orderId, itemId, quantity }
        //throw new OrderProcessFailedError(orderId)
    }
}
