package com.storiqa.storiqawallet.data.network.responses

import com.google.gson.annotations.SerializedName
import com.storiqa.storiqawallet.data.model.Currency

data class FeeResponse(

        @SerializedName("currency")
        val currency: Currency,

        @SerializedName("fees")
        val fees: List<Fee>)

data class Fee(

        @SerializedName("value")
        val value: String,

        @SerializedName("estimatedTime")
        val estimatedTime: Int)