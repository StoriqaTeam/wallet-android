package com.storiqa.storiqawallet.data.model

data class Card(
        val id: String,
        val userId: Long,
        val balance: String,
        val balanceFormatted: String,
        val balanceFiat: String,
        val currency: String,
        val currencyIcon: Int,
        val accountAddress: String,
        val name: String)