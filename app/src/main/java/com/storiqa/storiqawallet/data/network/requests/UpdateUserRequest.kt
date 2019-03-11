package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(

        @SerializedName("phone")
        val phone: String? = null,

        @SerializedName("firstName")
        val firstName: String,

        @SerializedName("lastName")
        val lastName: String
)