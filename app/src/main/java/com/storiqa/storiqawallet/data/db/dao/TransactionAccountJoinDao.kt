package com.storiqa.storiqawallet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storiqa.storiqawallet.data.db.entity.TransactionAccountEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionAccountJoin
import com.storiqa.storiqawallet.data.db.entity.TransactionEntity

@Dao
interface TransactionAccountJoinDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transactionAccountJoin: TransactionAccountJoin)

    @Query("SELECT * FROM Transactions INNER JOIN TransactionAccountJoins ON Transactions.id=TransactionAccountJoins.transaction_id WHERE TransactionAccountJoins.blockchain_address=:blockchainAddress")
    fun getUsersForRepository(blockchainAddress: String): List<TransactionEntity>

    @Query("SELECT * FROM TransactionAccounts INNER JOIN TransactionAccountJoins ON TransactionAccounts.blockchain_address=TransactionAccountJoins.blockchain_address WHERE TransactionAccountJoins.transaction_id=:transactionAccountId")
    fun getRepositoriesForUsers(transactionAccountId: String): List<TransactionAccountEntity>
}