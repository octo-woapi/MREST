const accounts = [
    {number: 'R4CH0', balance: 8000},
    {number: 'R4CL0', balance: 10000},
    {number: 'C4SS0', balance: 3000},
    {number: 'CL0D0', balance: 100},
    {number: 'D34L0', balance: 100000},
    {number: 'PR010', balance: 2000}]

export const COMPANY_ACCOUNT_NUMBER = 'D34L0'

export class AccountRepository {
    async get(accountNumber) {
        return accounts.find(account => account.number === accountNumber.toUpperCase())
    }

    async makeTransaction(debitAccountNumber, creditAccountNumber, amount) {
        const debitAccountIndex = this.getAccountIndex(debitAccountNumber)
        const creditAccountIndex = this.getAccountIndex(creditAccountNumber)
        if (debitAccountIndex >= 0 && creditAccountIndex >= 0) {
            accounts[debitAccountIndex].balance -= amount
            accounts[creditAccountIndex].balance += amount
        }
    }

    getAccountIndex(accountNumber) {
        return accounts.findIndex((account => account.number === accountNumber))
    }
}
