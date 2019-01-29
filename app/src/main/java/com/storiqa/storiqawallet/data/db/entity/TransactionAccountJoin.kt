package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "TransactionAccountJoins", primaryKeys = ["transaction_id", "blockchain_address"],
        foreignKeys = [
            ForeignKey(entity = TransactionEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("transaction_id")),
            ForeignKey(entity = TransactionAccountEntity::class,
                    parentColumns = arrayOf("blockchain_address"),
                    childColumns = arrayOf("blockchain_address"))])
class TransactionAccountJoin(
        @ColumnInfo(name = "transaction_id") val transactionId: String,
        @ColumnInfo(name = "blockchain_address") val blockchainAddress: String)