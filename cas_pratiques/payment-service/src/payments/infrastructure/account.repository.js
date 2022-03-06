const accounts = [
    { number: 'R4CL0', balance: 5000 },
    { number: 'R4CH0', balance: 6000 },
    { number: 'CH3M0', balance: 800 },
    { number: 'CL0D0', balance: 3000 },
    { number: 'TR150', balance: 9000 },
    { number: 'PR010', balance: 30000 }]

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
