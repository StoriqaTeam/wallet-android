package com.storiqa.storiqawallet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storiqa.storiqawallet.data.db.entity.Rate
import io.reactivex.Flowable

@Dao
interface RateDao {
    @Query("SELECT * FROM rate")
    fun loadRatesFlowable(): Flowable<List<Rate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate: Rate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rates: List<Rate>)

    @Query("DELETE FROM rate")
    fun deleteAll()
}