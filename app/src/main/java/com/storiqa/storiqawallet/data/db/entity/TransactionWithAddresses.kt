package com.storiqa.storiqawallet.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithAddresses(
        @Embedded val transaction: TransactionEntity,
        @Relation(
                parentColumn = "id",
                entityColumn = "transaction_id",
                entity = TransactionAccountJoin::class,
                projection = ["blockchain_address"])
        val fromAccount: List<TransactionAccountEntity>,
        @Relation(parentColumn = "to_address_id",
                entityColumn = "blockchain_address")
        val toAccount: List<TransactionAccountEntity>
)