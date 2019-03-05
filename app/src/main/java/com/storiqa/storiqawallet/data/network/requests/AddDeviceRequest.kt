package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class AddDeviceRequest(

        @SerializedName("userId")
        val userId: Long,

        @SerializedName("deviceOs")
        val deviceOs: String,

        @SerializedName("deviceId")
        val deviceId: String,

        @SerializedName("publicKey")
        val publicKey: String
)