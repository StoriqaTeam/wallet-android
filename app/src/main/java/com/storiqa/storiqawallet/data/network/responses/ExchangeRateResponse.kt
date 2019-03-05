package com.storiqa.storiqawallet.data.network.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ExchangeRateResponse(

        @SerializedName("id")
        val id: String,

        @SerializedName("from")
        val from: String,

        @SerializedName("to")
        val to: String,

        @SerializedName("amount")
        val amount: BigDecimal,

        @SerializedName("rate")
        val rate: Double,

        @SerializedName("expiration")
        val expiration: String
)