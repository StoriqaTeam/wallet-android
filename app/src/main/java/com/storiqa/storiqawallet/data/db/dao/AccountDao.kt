package com.storiqa.storiqawallet.data.db.dao

import androidx.room.*
import com.storiqa.storiqawallet.data.db.entity.Account
import io.reactivex.Flowable


@Dao
interface AccountDao {
    @Query("SELECT * FROM account WHERE user_id IN (:userId)")
    fun loadAccounts(userId: Long): Flowable<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Transaction
    fun deleteAndInsertAll(accounts: List<Account>) {
        deleteAll()
        insertAll(accounts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(accounts: List<Account>)

    @Query("DELETE FROM account")
    fun deleteAll()
}