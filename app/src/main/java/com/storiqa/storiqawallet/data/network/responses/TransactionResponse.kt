package com.storiqa.storiqawallet.data.network.responses

import com.google.gson.annotations.SerializedName
import com.storiqa.storiqawallet.data.model.Currency

data class TransactionResponse(
        val id: String,
        val fromValue: String,
        val fromCurrency: Currency,
        val toValue: String,
        val toCurrency: Currency,
        val fee: String,
        val status: String,
        val createdAt: String,
        val updatedAt: String,
        val blockchainTxIds: List<String>,

        @SerializedName("to")
        val toAccount: TransactionAccountResponse,

        @SerializedName("from")
        val fromAccount: List<TransactionAccountResponse>,

        val fiatValue: String?,
        val fiatCurrency: Currency?)

data class TransactionAccountResponse(
        @SerializedName("account_id")
        val accountId: String?,

        @SerializedName("owner_name")
        val ownerName: String?,

        @SerializedName("blockchain_address")
        val blockchainAddress: String)