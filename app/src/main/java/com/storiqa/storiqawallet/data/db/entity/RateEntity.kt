package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.storiqa.storiqawallet.data.model.Currency

@Entity(tableName = "Rates", primaryKeys = ["currency_crypto", "currency_fiat"])
data class RateEntity(
        @ColumnInfo(name = "currency_crypto") var currencyCrypto: Currency,
        @ColumnInfo(name = "currency_fiat") var currencyFiat: Currency,
        @ColumnInfo(name = "price") var price: Double)