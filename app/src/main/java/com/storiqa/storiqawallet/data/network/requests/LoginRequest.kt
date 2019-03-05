package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(

        @SerializedName("email")
        val email: String,

        @SerializedName("password")
        val password: String,

        @SerializedName("deviceOs")
        val deviceOs: String,

        @SerializedName("deviceId")
        val deviceId: String
) {
    @SerializedName("deviceType")
    val deviceType = "android"

}