import { AccountRepository } from './payments/infrastructure/account.repository.js'
import {createPaymentAndTriggerEventUsecaseFactory} from './payments/domain/create-payment-and-trigger-event.usecase.js'
import {getBalanceUsecaseFactory} from './payments/domain/get-balance.usecase.js'
import {createPaymentUsecaseFactory} from './payments/domain/create-payment.usecase.js'
import broker from './kafka.js'
import {CandyRepository} from './payments/infrastructure/candy.repository.js'
const messageProducer = broker.producer

const accountRepository = new AccountRepository()
const itemRepository = new CandyRepository()
const createPayment = createPaymentUsecaseFactory(accountRepository, itemRepository)
const createPaymentAndTriggerEvent = createPaymentAndTriggerEventUsecaseFactory(accountRepository, itemRepository, messageProducer)
const getBalance = getBalanceUsecaseFactory(accountRepository)

export default {
    CreatePayment: createPayment,
    CreatePaymentAndTriggerEvent: createPaymentAndTriggerEvent,
    GetBalance: getBalance,
}
