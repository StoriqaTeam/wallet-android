package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class FeeRequest(

        @SerializedName("currency")
        val currency: String,

        @SerializedName("accountAddress")
        val accountAddress: String
)
