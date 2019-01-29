package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "TransactionAccounts",
        primaryKeys = ["blockchain_address"],
        indices = [Index(value = arrayOf("blockchain_address"))])
data class TransactionAccountEntity(
        @ColumnInfo(name = "blockchain_address") val blockchainAddress: String,
        @ColumnInfo(name = "account_id") var accountId: String?,
        @ColumnInfo(name = "owner_name") val ownerName: String?)