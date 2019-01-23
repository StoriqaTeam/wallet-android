package com.storiqa.storiqawallet.data.db.dao

import androidx.room.*
import com.storiqa.storiqawallet.data.db.entity.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM Users WHERE id IN (:id)")
    fun loadUserById(id: Long): Flowable<UserEntity>

    @Query("SELECT * FROM Users WHERE email LIKE (:email) LIMIT 1")
    fun loadUserFlowable(email: String): Flowable<UserEntity>

    @Query("SELECT * FROM Users WHERE email LIKE (:email) LIMIT 1")
    fun loadUserSingle(email: String): Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}