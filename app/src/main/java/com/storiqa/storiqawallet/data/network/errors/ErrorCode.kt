package com.storiqa.storiqawallet.data.network.errors

import com.google.gson.annotations.SerializedName

enum class ErrorCode {

    @SerializedName("not_valid")
    INVALID_EMAIL,

    @SerializedName("not_exists", alternate = ["email"])
    NOT_EXISTS,

    @SerializedName("blocked")
    BLOCKED,

    @SerializedName("not_provided")
    NOT_PROVIDED,

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
    WRONG_DEVICE_ID,

    @SerializedName("expired")
    EXPIRED,//token or exchange rate

    @SerializedName("currency")
    DIFFERENT_CURRENCY,

    @SerializedName("not_enough_on_market")
    NOT_ENOUGH_ON_MARKET,

    @SerializedName("not_found")
    NOT_FOUND_EXCHANGE_RATE,

    @SerializedName("limit")
    EXCHANGE_WRONG_LIMIT,

    @SerializedName("not_enough_balance")
    NOT_ENOUGH_BALANCE,

    @SerializedName("revoked")
    TOKEN_REVOKED
}
