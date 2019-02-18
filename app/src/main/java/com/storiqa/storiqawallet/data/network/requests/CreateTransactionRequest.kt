package com.storiqa.storiqawallet.data.network.requests

data class CreateTransactionRequest(
        val id: String,
        val userId: Long,
        val from: String,                   //id account
        val to: String,                     //blockchain address
        val toType: String,
        val toCurrency: String,
        val value: String,
        val valueCurrency: String,
        val fiatValue: String,
        val fiatCurrency: String,
        val fee: String,
        val exchangeId: String? = null,
        val exchangeRate: String? = null
)