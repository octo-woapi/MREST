import {Kafka} from 'kafkajs'

const kafka = new Kafka({
    clientId: 'payment-service',
    brokers: ['localhost:9092'],
    retry: {
        initialRetryTime: 1000,
        retries: 3
    }
})

const consumer = kafka.consumer({groupId: 'stock-service-consumer'})
consumer.connect()
consumer.subscribe({topic: 'stock'})
const producer = kafka.producer()
producer.connect()

export default { consumer, producer }