import {AccountNoFoundError} from './payments.errors.js'

export function getBalanceUsecaseFactory(accountRepository) {
    return async (accountNumber) => {
        const account = await accountRepository.get(accountNumber)
        if (account) return account
        throw new AccountNoFoundError(accountNumber)
    }
}
