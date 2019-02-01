package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.common.CurrencyFormatter
import com.storiqa.storiqawallet.data.db.entity.TransactionAccountEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionWithAddresses
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.model.TransactionAccount
import com.storiqa.storiqawallet.utils.getPresentableTime
import java.math.BigDecimal

class TransactionMapper(private val transactionAccounts: List<TransactionAccountEntity>) : ITransactionMapper {

    private val currencyFormatter = CurrencyFormatter()

    override fun map(transaction: TransactionWithAddresses): Transaction {
        val toAccount = TransactionAccount(
                transaction.toAccount[0].blockchainAddress,
                transaction.toAccount[0].accountId,
                transaction.toAccount[0].ownerName)

        val fromAccount = ArrayList<TransactionAccount>()
        transaction.fromAccount.forEach {
            val account = getAccountByAddress(it)
            fromAccount.add(TransactionAccount(
                    account.blockchainAddress,
                    account.accountId,
                    account.ownerName))
        }
        val tr = transaction.transaction

        val amountDecimal = getAmount(tr)

        val balanceDecimal = currencyFormatter.getFormattedDecimal(amountDecimal.toPlainString(), tr.fromCurrency)
        val amountFormatted = currencyFormatter.getBalanceWithoutSymbol(balanceDecimal, tr.fromCurrency)
        val amountFiatFormatted = if (tr.fiatValue != null && tr.fiatCurrency != null)
            tr.fiatCurrency?.getSymbol() + " " + tr.fiatValue
        else
            ""

        return Transaction(
                tr.id,
                tr.fromValue,
                tr.fromCurrency,
                tr.toValue,
                tr.toCurrency,
                tr.fee,
                tr.status,
                tr.createdAt,
                toAccount,
                fromAccount,
                tr.fiatValue,
                tr.fiatCurrency,

                getPresentableTime(tr.createdAt),
                amountFormatted,
                amountFiatFormatted)
    }

    override fun map(transactions: List<TransactionWithAddresses>): List<Transaction> {
        val newTransactions = ArrayList<Transaction>()
        transactions.forEach { newTransactions.add(map(it)) }
        return newTransactions
    }

    private fun getAccountByAddress(address: String): TransactionAccountEntity {
        for (account in transactionAccounts) {
            if (account.blockchainAddress == address)
                return account
        }

        throw Exception("account not found")
    }

    private fun getAmount(transaction: TransactionEntity): BigDecimal {
        val toValue = BigDecimal(transaction.toValue)
        val fromValue = BigDecimal(transaction.fromValue)
        return toValue.subtract(fromValue)
    }
}