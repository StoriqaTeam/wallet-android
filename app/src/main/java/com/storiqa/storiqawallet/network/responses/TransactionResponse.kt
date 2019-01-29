package com.storiqa.storiqawallet.network.responses

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
        val id: String,
        val fromValue: String,
        val fromCurrency: String,
        val toValue: String,
        val toCurrency: String,
        val fee: String,
        val status: String,
        val createdAt: String,
        val updatedAt: String,
        val blockchainTxIds: List<String>,

        @SerializedName("to")
        val toAccount: TransactionAccount,

        @SerializedName("from")
        val fromAccount: List<TransactionAccount>,

        val fiatValue: String?,
        val fiatCurrency: String?)

data class TransactionAccount(
        @SerializedName("account_id")
        val accountId: String?,

        @SerializedName("owner_name")
        val ownerName: String?,

        @SerializedName("blockchain_address")
        val blockchainAddress: String)