package com.storiqa.storiqawallet.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}