import {AccountNoFoundError, InsufficientBalanceError} from './payments.errors.js'
import {COMPANY_ACCOUNT_NUMBER} from '../infrastructure/account.repository.js'

export function createPaymentUsecaseFactory(accountRepository, itemRepository) {
    return async (clientId, itemId, quantity) => {
        const item = await itemRepository.get(itemId)
        const amount = item.price * parseInt(quantity)
        const debitAccount = await accountRepository.get(clientId)
        if (!debitAccount) throw new AccountNoFoundError(debitAccount.number)
        if (debitAccount.balance < amount) throw new InsufficientBalanceError(debitAccount.number)
        
        await accountRepository.makeTransaction(debitAccount.number, COMPANY_ACCOUNT_NUMBER, amount)
    }
}
