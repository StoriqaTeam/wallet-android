package com.storiqa.storiqawallet.network.errors

import com.google.gson.annotations.SerializedName

/*enum class ErrorCode {

    @SerializedName("email")
    INVALID_EMAIL,

    @SerializedName("email")
    NOT_EXIST,

    @SerializedName("password")
    INVALID_PASSWORD,

    @SerializedName("exists")
    DEVICE_NOT_ATTACHED,

    @SerializedName("exists")
    ALREADY_EXISTS,

    @SerializedName("upper case")
    NO_UPPER_CASE_CHARACTER,

    @SerializedName("len")
    INVALID_LENGTH,

    @SerializedName("numbers")
    NO_NUMBER,

    @SerializedName("not_verified")
    NOT_VERIFIED,

    @SerializedName("email_timeout")
    EMAIL_TIMEOUT

}*/

enum class ErrorCode {

    @SerializedName("not_valid")
    INVALID_EMAIL,

    @SerializedName("email")
    EMAIL_NOT_FOUND,

    @SerializedName("not_exists")
    NOT_EXISTS,

    @SerializedName("exists")
    ALREADY_EXISTS,

    @SerializedName("password")
    INVALID_PASSWORD,

    @SerializedName("upper case")
    NO_UPPER_CASE_CHARACTER,

    @SerializedName("len")
    INVALID_LENGTH,

    @SerializedName("numbers")
    NO_NUMBER,

    @SerializedName("not_verified")
    NOT_VERIFIED,

    @SerializedName("email_timeout")
    EMAIL_TIMEOUT,

    @SerializedName("device_id")
    WRONG_DEVICE_ID

}
