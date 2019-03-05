package com.storiqa.storiqawallet.data.network.responses

import com.google.gson.annotations.SerializedName
import com.storiqa.storiqawallet.data.model.Currency

data class TransactionResponse(

        @SerializedName("id")
        val id: String,

        @SerializedName("fromValue")
        val fromValue: String,

        @SerializedName("fromCurrency")
        val fromCurrency: Currency,

        @SerializedName("toValue")
        val toValue: String,

        @SerializedName("toCurrency")
        val toCurrency: Currency,

        @SerializedName("fee")
        val fee: String,

        @SerializedName("status")
        val status: String,

        @SerializedName("createdAt")
        val createdAt: String,

        @SerializedName("updatedAt")
        val updatedAt: String,

        @SerializedName("blockchainTxIds")
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