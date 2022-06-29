let candies = [
    { id: 'brow', price: 40, name: 'brown sugar', sugar_level: 'down', description: 'provoque la pertes des dents' },
    { id: 'coke', price: 70, name: 'coke cola', sugar_level: 'high', description: 'pique le nez' },
    { id: 'crys', price: 15, name: 'crystal mint', sugar_level: 'high', description: 'à n\'en plus dormir' },
    { id: 'cake', price: 20, name: 'spice cake', sugar_level: 'down', description: 'étrange' },
    { id: 'acid', price: 30, name: 'acid flying saucer', sugar_level: 'down', description: 'un voyage gustatif' },
    { id: 'bloo', price: 35, name: 'bloody molly', sugar_level: 'high', description: 'très tendre' },
]

export class CandyRepository {
    async get(itemId = undefined) {
        if (itemId) return candies.find(candy => candy.id === itemId)
    }
}
