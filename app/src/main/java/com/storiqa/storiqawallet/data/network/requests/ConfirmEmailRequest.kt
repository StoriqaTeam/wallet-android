package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class ConfirmEmailRequest(

        @SerializedName("emailConfirmToken")
        val emailConfirmToken: String
)