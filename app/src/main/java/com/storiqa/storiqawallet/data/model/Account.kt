package com.storiqa.storiqawallet.data.model

data class Account(
        val id: String,
        val userId: Long,
        val balance: String,
        val currency: String,
        val accountAddress: String,
        val name: String,
        val erc20Approved: Boolean)