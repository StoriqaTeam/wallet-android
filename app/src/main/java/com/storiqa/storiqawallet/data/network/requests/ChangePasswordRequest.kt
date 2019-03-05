package com.storiqa.storiqawallet.data.network.requests

import com.google.gson.annotations.SerializedName

class ChangePasswordRequest(

        @SerializedName("oldPassword")
        val oldPassword: String,

        @SerializedName("newPassword")
        val newPassword: String
)