package com.storiqa.storiqawallet.data.db.dao

import android.arch.persistence.room.*
import com.storiqa.storiqawallet.data.db.entity.Account
import io.reactivex.Flowable

@Dao
interface AccountDao {
    @Query("SELECT * FROM account WHERE user_id IN (:userId)")
    fun loadAccounts(userId: Long): Flowable<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Delete
    fun deleteNotes(vararg account: Account)
}