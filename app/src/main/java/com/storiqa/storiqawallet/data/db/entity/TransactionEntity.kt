package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.storiqa.storiqawallet.data.model.Currency

@Entity(tableName = "Transactions",
        indices = [Index(value = arrayOf("id"))])
data class TransactionEntity(
        @PrimaryKey var id: String,
        @ColumnInfo(name = "to_address_id") val toAccount: String,
        @ColumnInfo(name = "from_value") val fromValue: String,
        @ColumnInfo(name = "from_currency") val fromCurrency: Currency,
        @ColumnInfo(name = "to_value") val toValue: String,
        @ColumnInfo(name = "to_currency") val toCurrency: Currency,
        @ColumnInfo(name = "fee") val fee: String,
        @ColumnInfo(name = "created_at") val createdAt: Long,
        @ColumnInfo(name = "updated_at") val updatedAt: Long,
        @ColumnInfo(name = "status") val status: String,
        @ColumnInfo(name = "fiat_value") val fiatValue: String?,
        @ColumnInfo(name = "fiat_currency") val fiatCurrency: Currency?)