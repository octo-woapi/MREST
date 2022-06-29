export function getStocksUsecaseFactory (stockRepository) {
    return async () => {
        return await stockRepository.get()
    }
}
