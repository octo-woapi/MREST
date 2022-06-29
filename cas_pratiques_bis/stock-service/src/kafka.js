import {Kafka} from 'kafkajs'

const kafka = new Kafka({
    clientId: 'stock-service',
    brokers: ['localhost:9092'],
    retry: {
        initialRetryTime: 1000,
        retries: 3
    }
})

const consumer = kafka.consumer({groupId: 'order-service-consumer'})
consumer.connect()
consumer.subscribe({topic: 'order'})
const producer = kafka.producer()
producer.connect()

export default { consumer, producer }