export function removeFromStockAndSendEventUsecaseFactory(stockRepository, producer) {
    return async (orderId, clientId, id, quantity) => {
        const stock = stockRepository.get(id)

        if (stock.units < quantity) {
            return await producer.send({
                topic: 'stock',
                messages: [
                    {key: 'stock-unvalidated', value: `{"orderId": "${orderId}", "clientId": "${clientId}", "itemId": "${id}", "quantity": "${quantity}"}`}
                ]
            })
        }

        stockRepository.update(id, stock.units - quantity)
        await producer.send({
            topic: 'stock',
            messages: [
                {key: 'stock-validated', value: `{"orderId": "${orderId}", "clientId": "${clientId}", "itemId": "${id}", "quantity": "${quantity}"}`}
            ]
        })
    }
}
