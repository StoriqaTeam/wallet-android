package com.storiqa.storiqawallet.data.network.responses

import com.storiqa.storiqawallet.data.model.Currency

data class AccountResponse(
        val id: String,
        val userId: Long,
        val balance: String,
        val currency: Currency,
        val accountAddress: String,
        val name: String,
        val erc20Approved: Boolean)