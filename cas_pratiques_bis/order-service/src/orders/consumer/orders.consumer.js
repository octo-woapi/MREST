export default function (container) {
    return async ({message}) => {
        const {event, payload} = parseMessage(message)
        
        switch (event) {
        // Ici l'événement 'event-key' est consommé et le usecase FaireUnTruc est exécutée
        // les traitements effectués sont décrits dans src/orders/domain/faire-un-truc.usecase.js
        case 'event-key':
            return await container.FaireUnTruc(payload)
        // case 'other-event-key':
        //     return await container.monSecondNouveauUsecase(payload)
        default:
            return
        }
    }
}

const parseMessage = (message) => {
    const event = message.key.toString()
    const payload = JSON.parse(message.value.toString())
    console.log('message consumed')
    console.log({key: event, payload: payload})
    return {event, payload}
}
