package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(

        @SerializedName("email")
        val email: String,

        @SerializedName("phone")
        val phone: String,

        @SerializedName("password")
        val password: String,

        @SerializedName("firstName")
        val firstName: String,

        @SerializedName("lastName")
        val lastName: String,

        @SerializedName("deviceOs")
        val deviceOs: String,

        @SerializedName("deviceId")
        val deviceId: String,

        @SerializedName("publicKey")
        val publicKey: String
) {
    @SerializedName("deviceType")
    val deviceType = "android"

}