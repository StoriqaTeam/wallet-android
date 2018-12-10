package com.storiqa.storiqawallet.network.errors

import com.google.gson.annotations.SerializedName

enum class ErrorCode {

    @SerializedName("email")
    EMAIL_NOT_VALID,

    @SerializedName("email")
    EMAIL_NOT_FOUND,

    @SerializedName("password")
    WRONG_PASSWORD,

    @SerializedName("exists")
    DEVICE_NOT_ATTACHED

}