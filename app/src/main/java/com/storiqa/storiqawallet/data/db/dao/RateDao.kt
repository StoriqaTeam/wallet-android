package com.storiqa.storiqawallet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import io.reactivex.Flowable

@Dao
interface RateDao {
    @Query("SELECT * FROM Rates")
    fun loadRatesFlowable(): Flowable<List<RateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate: RateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rates: List<RateEntity>)

    @Query("DELETE FROM Rates")
    fun deleteAll()
}