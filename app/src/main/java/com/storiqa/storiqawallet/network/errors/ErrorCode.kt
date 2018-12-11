package com.storiqa.storiqawallet.network.errors

import com.google.gson.annotations.SerializedName

enum class ErrorCode {

    @SerializedName("email")
    INVALID_EMAIL,

    @SerializedName("not_found")
    NOT_FOUND,

    @SerializedName("password")
    INVALID_PASSWORD,

    @SerializedName("exists")
    ALREADY_EXISTS

}