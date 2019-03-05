package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

class ResendConfirmEmailRequest(

        @SerializedName("email")
        val email: String
) {
    @SerializedName("deviceType")
    val deviceType = "android"

}