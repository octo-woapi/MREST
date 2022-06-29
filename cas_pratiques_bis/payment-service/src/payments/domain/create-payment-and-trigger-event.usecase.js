import {COMPANY_ACCOUNT_NUMBER} from '../infrastructure/account.repository.js'

export function createPaymentAndTriggerEventUsecaseFactory(accountRepository, itemRepository, producer) {
    return async (orderId, clientId, itemId, quantity) => {
        console.log(COMPANY_ACCOUNT_NUMBER)

        await producer.send({
            topic: 'payment',
            messages: [
                {key: 'eventName', value: `{"orderId": "${orderId}", "clientId": "${clientId}", "itemId": "${itemId}", "quantity": "${quantity}"}`}
            ]
        })
    }
}
