package com.storiqa.storiqawallet.data.db.dao

import androidx.room.*
import com.storiqa.storiqawallet.data.db.entity.User
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id IN (:id)")
    fun loadUserById(id: Long): Flowable<User>

    @Query("SELECT * FROM user WHERE email LIKE (:email) LIMIT 1")
    fun loadUserFlowable(email: String): Flowable<User>

    @Query("SELECT * FROM user WHERE email LIKE (:email) LIMIT 1")
    fun loadUserSingle(email: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}