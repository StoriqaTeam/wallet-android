package com.storiqa.storiqawallet.data.db.entity

import android.arch.persistence.room.*

@Entity(indices = [Index(value = arrayOf("id", "user_id"))],
        foreignKeys = [ForeignKey(
                entity = User::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("user_id"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)])
data class Account(
        @PrimaryKey var id: Long,
        @ColumnInfo(name = "user_id") val userId: Long,
        @ColumnInfo(name = "balance") val balance: String,
        @ColumnInfo(name = "currency") val currency: String,
        @ColumnInfo(name = "account_address") val accountAddress: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "erc_approved") val erc20Approved: Boolean)