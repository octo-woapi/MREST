import { AccountNoFoundError, InsufficientBalanceError } from './payments.errors.js'

export function createPaymentUsecaseFactory(accountRepository) {
    return async (debitAccountNumber, creditAccountNumber, amount) => {
        const debitAccount = await accountRepository.get(debitAccountNumber)
        if (!debitAccount) {
            throw new AccountNoFoundError(debitAccountNumber)
        }
        if (debitAccount.balance < amount) {
            throw new InsufficientBalanceError(debitAccountNumber)
        }

        const creditAccount = await accountRepository.get(creditAccountNumber)
        if (!creditAccount) {
            throw new AccountNoFoundError(creditAccountNumber)
        }

        await accountRepository.makeTransaction(debitAccountNumber, creditAccountNumber, amount)
    }
}
