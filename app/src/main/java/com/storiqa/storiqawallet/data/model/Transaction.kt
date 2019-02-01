package com.storiqa.storiqawallet.data.model

data class Transaction(
        val id: String,
        val fromValue: String,
        val fromCurrency: Currency,
        val toValue: String,
        val toCurrency: Currency,
        val fee: String,
        val status: String,
        val createdAt: Long,
        val toAccount: TransactionAccount,
        val fromAccount: List<TransactionAccount>,
        val fiatValue: String?,
        val fiatCurrency: Currency?,

        val time: String,
        val amount: String,
        val fiat: String?
)

data class TransactionAccount(
        val blockchainAddress: String,
        val accountId: String?,
        val ownerName: String?)