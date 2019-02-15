package com.storiqa.storiqawallet.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
        val id: String,
        val userId: Long,
        val balance: String,
        val balanceFormatted: String,
        val balanceFiat: String,
        val background: Int,
        val currency: Currency,
        val currencyIcon: Int,
        val accountAddress: String,
        val name: String) : Parcelable