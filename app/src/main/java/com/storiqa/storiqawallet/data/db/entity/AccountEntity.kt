package com.storiqa.storiqawallet.data.db.entity

import androidx.room.*
import com.storiqa.storiqawallet.data.model.Currency
import com.storiqa.storiqawallet.data.network.responses.AccountResponse

@Entity(tableName = "Accounts",
        indices = [Index(value = arrayOf("id", "user_id"))],
        foreignKeys = [ForeignKey(
                entity = UserEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("user_id"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)])
data class AccountEntity(
        @PrimaryKey var id: String,
        @ColumnInfo(name = "user_id") val userId: Long,
        @ColumnInfo(name = "balance") val balance: String,
        @ColumnInfo(name = "currency") val currency: Currency,
        @ColumnInfo(name = "account_address") val accountAddress: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "erc_approved") val erc20Approved: Boolean) {
    constructor(account: AccountResponse) : this(account.id, account.userId, account.balance,
            account.currency, account.accountAddress, account.name, account.erc20Approved)
}