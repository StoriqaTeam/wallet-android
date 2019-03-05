package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class ConfirmResetPasswordRequest(

        @SerializedName("token")
        val token: String,

        @SerializedName("password")
        val password: String
)