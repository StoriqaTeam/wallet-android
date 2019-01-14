package com.storiqa.storiqawallet.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.storiqa.storiqawallet.data.db.dao.AccountDao
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.Account
import com.storiqa.storiqawallet.data.db.entity.User

@Database(entities = [User::class, Account::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun accountDao(): AccountDao
}