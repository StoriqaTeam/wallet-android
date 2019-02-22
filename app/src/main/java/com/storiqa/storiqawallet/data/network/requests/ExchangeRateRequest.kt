package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ExchangeRateRequest(

        @SerializedName("id")
        val id: String,

        @SerializedName("from")
        val from: String,

        @SerializedName("to")
        val to: String,

        @SerializedName("amountCurrency")
        val amountCurrency: String,

        @SerializedName("amount")
        val amount: BigDecimal
)