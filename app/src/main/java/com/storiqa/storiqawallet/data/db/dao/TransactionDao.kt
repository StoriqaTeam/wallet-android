package com.storiqa.storiqawallet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storiqa.storiqawallet.data.db.entity.TransactionEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionWithAddresses
import io.reactivex.Flowable

@Dao
interface TransactionDao {
    @Query("SELECT * FROM Transactions ORDER BY created_at DESC")
    fun loadAllTransactions(): Flowable<List<TransactionWithAddresses>>

    @Query("SELECT created_at FROM Transactions WHERE status='pending' ORDER BY created_at LIMIT 1")
    fun getOldestPendingTransactionTime(): Long

    @Query("SELECT created_at FROM Transactions ORDER BY created_at DESC LIMIT 1")
    fun getLastTransactionTime(): Long

    @Query("SELECT * FROM Transactions LEFT JOIN TransactionAccountJoins ON Transactions.id=TransactionAccountJoins.transaction_id WHERE to_address_id=:address OR blockchain_address=:address ORDER BY created_at DESC")
    fun loadAllTransactionsByAddress(address: String): Flowable<List<TransactionWithAddresses>>

    @Query("SELECT * FROM Transactions LEFT JOIN TransactionAccountJoins ON Transactions.id=TransactionAccountJoins.transaction_id WHERE to_address_id=:address OR blockchain_address=:address ORDER BY created_at DESC LIMIT :limit")
    fun loadTransactionsByAddress(address: String, limit: Int): Flowable<List<TransactionWithAddresses>>

    @Query("SELECT * FROM Transactions WHERE id=:id")
    fun loadTransactionById(id: String): Flowable<TransactionWithAddresses>

    @Query("SELECT * FROM Transactions ORDER BY created_at DESC LIMIT :limit")
    fun loadTransactionsWithLimit(limit: Int): Flowable<List<TransactionWithAddresses>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(transactions: List<TransactionEntity>)

    @Query("DELETE FROM Transactions")
    fun deleteAll()
}