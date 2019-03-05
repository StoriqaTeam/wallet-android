package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class RefreshRateRequest(

        @SerializedName("rateId")
        val rateId: String
)