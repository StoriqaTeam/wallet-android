package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Rates", primaryKeys = ["currency_crypto", "currency_fiat"])
data class RateEntity(
        @ColumnInfo(name = "currency_crypto") var currencyCrypto: String,
        @ColumnInfo(name = "currency_fiat") var currencyFiat: String,
        @ColumnInfo(name = "price") var price: Double)