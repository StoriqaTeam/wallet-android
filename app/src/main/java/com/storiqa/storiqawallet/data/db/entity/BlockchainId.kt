package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "BlockchainIds", primaryKeys = ["blockchain_id", "transaction_id"],
        foreignKeys = [
            ForeignKey(entity = TransactionEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("transaction_id"))])
class BlockchainId(
        @ColumnInfo(name = "blockchain_id") val blockchainId: String,
        @ColumnInfo(name = "transaction_id") val transactionId: String)