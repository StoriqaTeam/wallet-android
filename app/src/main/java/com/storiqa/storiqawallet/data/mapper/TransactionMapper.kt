package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.CurrencyFormatter
import com.storiqa.storiqawallet.data.db.entity.TransactionAccountEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionWithAddresses
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.model.TransactionAccount
import com.storiqa.storiqawallet.data.model.TransactionType
import com.storiqa.storiqawallet.utils.getPresentableDate

class TransactionMapper(private val transactionAccounts: List<TransactionAccountEntity>) : ITransactionMapper {

    private val currencyFormatter = CurrencyFormatter()

    override fun map(transaction: TransactionWithAddresses, address: String): Transaction {
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

        val type = getTransactionType(tr, address)

        val typeDescription = when (type) {
            TransactionType.SEND -> App.res.getString(R.string.transaction_type_sent) + " " + tr.fromCurrency
            TransactionType.RECEIVE -> App.res.getString(R.string.transaction_type_received) + " " + tr.toCurrency
        }

        val amountFormatted = if (type == TransactionType.SEND)
            "- " + currencyFormatter.getFormattedAmount(tr.fromValue, tr.fromCurrency)
        else
            "+ " + currencyFormatter.getFormattedAmount(tr.toValue, tr.toCurrency)

        val amountFiatFormatted = if (tr.fiatValue != null && tr.fiatCurrency != null)
            "- " + currencyFormatter.getFormattedAmount(tr.fiatValue, tr.fiatCurrency).replace(" ", "")
        else
            ""

        var commission = ""
        if (type == TransactionType.SEND && tr.fee != "0") {
            val feeString = currencyFormatter.getFormattedAmount(tr.fee, tr.fromCurrency)
            commission = "${App.res.getString(R.string.text_commission)} $feeString"
        }

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

                type,
                typeDescription,
                getPresentableDate(tr.createdAt),
                amountFormatted,
                commission,
                amountFiatFormatted)
    }

    private fun getTransactionType(tr: TransactionEntity, address: String): TransactionType {
        return if (tr.toAccount == address)
            TransactionType.RECEIVE
        else
            TransactionType.SEND
    }

    override fun map(transactions: List<TransactionWithAddresses>, address: String): List<Transaction> {
        val newTransactions = ArrayList<Transaction>()
        transactions.forEach { newTransactions.add(map(it, address)) }
        return newTransactions
    }

    private fun getAccountByAddress(address: String): TransactionAccountEntity {
        for (account in transactionAccounts) {
            if (account.blockchainAddress == address)
                return account
        }

        throw Exception("account not found")
    }
}