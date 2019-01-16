package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rate(
        @ColumnInfo(name = "currency_crypto") var currencyCrypto: String,
        @ColumnInfo(name = "currency_fiat") var currencyFiat: String,
        @ColumnInfo(name = "price") var price: Double) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}