package com.storiqa.storiqawallet.data.db.dao

import androidx.room.*
import com.storiqa.storiqawallet.data.db.entity.Rate
import io.reactivex.Flowable

@Dao
interface RateDao {
    @Query("SELECT * FROM rate")
    fun loadRateFlowable(): Flowable<List<Rate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate: Rate)

    @Delete
    fun delete(rate: Rate)
}