package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class LoginByOauthRequest(

        @SerializedName("oauthToken")
        val oauthToken: String,

        @SerializedName("oauthProvider")
        val oauthProvider: String,

        @SerializedName("deviceOs")
        val deviceOs: String,

        @SerializedName("deviceId")
        val deviceId: String
) {
    @SerializedName("deviceType")
    val deviceType = "android"
}