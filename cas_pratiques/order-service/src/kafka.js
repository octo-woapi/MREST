import {Kafka} from 'kafkajs'

const kafka = new Kafka({
    clientId: 'order-service',
    brokers: ['localhost:9092'],
    retry: {
        initialRetryTime: 1000,
        retries: 3
    }
})

const consumer = kafka.consumer({groupId: 'payment-service-consumer'})
consumer.connect()
consumer.subscribe({topic: 'payment'})
const producer = kafka.producer()
producer.connect()

export default { consumer, producer }