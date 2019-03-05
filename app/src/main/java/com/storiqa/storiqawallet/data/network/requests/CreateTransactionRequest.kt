package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class CreateTransactionRequest(

        @SerializedName("id")
        val id: String,

        @SerializedName("userId")
        val userId: Long,

        @SerializedName("from")
        val from: String,                   //id account

        @SerializedName("to")
        val to: String,                     //blockchain address

        @SerializedName("toType")
        val toType: String,

        @SerializedName("toCurrency")
        val toCurrency: String,

        @SerializedName("value")
        val value: String,

        @SerializedName("valueCurrency")
        val valueCurrency: String,

        @SerializedName("fiatValue")
        val fiatValue: String? = null,

        @SerializedName("fiatCurrency")
        val fiatCurrency: String? = null,

        @SerializedName("fee")
        val fee: String = "0",

        @SerializedName("exchangeId")
        val exchangeId: String? = null,

        @SerializedName("exchangeRate")
        val exchangeRate: Double? = null
)