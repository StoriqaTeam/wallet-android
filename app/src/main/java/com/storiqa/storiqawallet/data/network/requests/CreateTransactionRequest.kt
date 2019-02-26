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
        val fiatValue: String? = null,
        val fiatCurrency: String? = null,
        val fee: String = "0",
        val exchangeId: String? = null,
        val exchangeRate: Double? = null
)