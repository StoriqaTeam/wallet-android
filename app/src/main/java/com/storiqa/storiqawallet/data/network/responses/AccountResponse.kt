package com.storiqa.storiqawallet.data.network.responses

import com.google.gson.annotations.SerializedName
import com.storiqa.storiqawallet.data.model.Currency

data class AccountResponse(

        @SerializedName("id")
        val id: String,

        @SerializedName("userId")
        val userId: Long,

        @SerializedName("balance")
        val balance: String,

        @SerializedName("currency")
        val currency: Currency,

        @SerializedName("accountAddress")
        val accountAddress: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("erc20Approved")
        val erc20Approved: Boolean)