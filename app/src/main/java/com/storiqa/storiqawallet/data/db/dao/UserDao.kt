package com.storiqa.storiqawallet.data.db.dao

import android.arch.persistence.room.*
import com.storiqa.storiqawallet.data.db.entity.User
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id IN (:id)")
    fun loadUserById(id: Long): Flowable<User>

    @Query("SELECT * FROM user WHERE email LIKE (:email) LIMIT 1")
    fun loadUserByEmail(email: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}