import {Status} from '../domain/status.js'

let orders = [
    {id: 1, itemId: 'brow', quantity: 123, status: Status.VALIDATED},
    {id: 2, itemId: 'coke', quantity: 12, status: Status.CANCELED},
    {id: 3, itemId: 'acid', quantity: 55, status: Status.VALIDATED},
    {id: 4, itemId: 'crys', quantity: 5, status: Status.CANCELED},
]

export class OrderRepository {
    async get(id = undefined) {
        if (id) return orders.find(order => order.id === id)
        return orders
    }

    async add(order) {
        order.id = order.id && isAvailableId(order.id) ? order.id : getAutoId()
        if (isObject(order)) orders.push(order)
        return order.id
    }

    async remove(id) {
        const index = orders.findIndex((order => order.id === id))
        orders.splice(index,1)
    }

    async update(order) {
        await this.remove(order.id)
        await this.add(order)
    }
}

const getAutoId = () =>
    orders.map(order => order.id).sort((a, b) => a - b)[orders.length - 1] + 1

const isObject = (object) => object === Object(object)
const isAvailableId = (id) => !orders.some(order => order.id === id)
