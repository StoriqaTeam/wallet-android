package com.storiqa.storiqawallet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.storiqa.storiqawallet.data.db.converter.CurrencyTypeConverter
import com.storiqa.storiqawallet.data.db.dao.AccountDao
import com.storiqa.storiqawallet.data.db.dao.RateDao
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.db.entity.UserEntity

@Database(entities = [UserEntity::class, AccountEntity::class, RateEntity::class], version = 1)
@TypeConverters(CurrencyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun accountDao(): AccountDao

    abstract fun rateDao(): RateDao
}