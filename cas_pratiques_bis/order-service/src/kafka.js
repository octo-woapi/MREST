import {Kafka, Partitioners} from 'kafkajs'

const kafka = new Kafka({
    clientId: 'order-service',
    brokers: ['localhost:9092']
})

const consumer = kafka.consumer({groupId: 'payment-service-consumer'})
consumer.connect()
consumer.subscribe({topic: 'payment'})

const producer = kafka.producer({
    createPartitioner: Partitioners.LegacyPartitioner
})

export default { consumer, producer }
