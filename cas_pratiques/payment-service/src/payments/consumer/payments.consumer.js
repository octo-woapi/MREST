export default function (container) {
    return async ({message}) => {
        const {event, payload} = parseMessage(message)

    }
}

const parseMessage = (message) => {
    const event = message.key.toString()
    const params = JSON.parse(message.value.toString())
    const payload = {
        orderId: parseInt(params.orderId),
        clientId: params.clientId,
        itemId: params.itemId,
        quantity: parseInt(params.quantity)
    }
    console.log({key: event, payload: payload})
    return {event, payload}
}
