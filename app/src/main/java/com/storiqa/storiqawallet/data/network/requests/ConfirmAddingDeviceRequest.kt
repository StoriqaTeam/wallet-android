package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

class ConfirmAddingDeviceRequest(

        @SerializedName("token")
        val token: String,

        @SerializedName("deviceId")
        val deviceId: String
)