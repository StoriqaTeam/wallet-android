package com.storiqa.storiqawallet.data.network.responses

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(

        @SerializedName("id")
        val id: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("phone")
        val phone: String,

        @SerializedName("firstName")
        val firstName: String,

        @SerializedName("lastName")
        val lastName: String)