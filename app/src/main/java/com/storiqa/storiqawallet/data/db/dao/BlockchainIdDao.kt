package com.storiqa.storiqawallet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storiqa.storiqawallet.data.db.entity.BlockchainId
import io.reactivex.Flowable

@Dao
interface BlockchainIdDao {
    @Query("SELECT * FROM BlockchainIds")
    fun loadBlockchainIds(): Flowable<List<BlockchainId>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(blockchainId: BlockchainId)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(blockchainIds: List<BlockchainId>)
}