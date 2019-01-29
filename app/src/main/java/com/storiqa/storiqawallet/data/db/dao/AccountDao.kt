package com.storiqa.storiqawallet.data.db.dao

import androidx.room.*
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import io.reactivex.Flowable

@Dao
interface AccountDao {
    @Query("SELECT * FROM Accounts WHERE user_id IN (:userId)")
    fun loadAccounts(userId: Long): Flowable<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: AccountEntity)

    @Transaction
    fun deleteAndInsertAll(accounts: List<AccountEntity>) {
        deleteAll()
        insertAll(accounts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(accounts: List<AccountEntity>)

    @Query("DELETE FROM Accounts")
    fun deleteAll()
}