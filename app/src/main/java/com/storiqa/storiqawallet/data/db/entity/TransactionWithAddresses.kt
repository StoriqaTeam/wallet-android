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
        val fromAccount: List<String>,

        @Relation(parentColumn = "to_address_id",
                entityColumn = "blockchain_address")
        val toAccount: List<TransactionAccountEntity>,

        @Relation(parentColumn = "id",
                entityColumn = "transaction_id",
                entity = BlockchainId::class,
                projection = ["blockchain_id"])
        val blockchainIds: List<String>
)