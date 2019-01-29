package com.storiqa.storiqawallet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.storiqa.storiqawallet.data.db.converter.CurrencyTypeConverter
import com.storiqa.storiqawallet.data.db.dao.*
import com.storiqa.storiqawallet.data.db.entity.*

@Database(entities = [UserEntity::class, AccountEntity::class, RateEntity::class,
    TransactionEntity::class, TransactionAccountEntity::class, TransactionAccountJoin::class], version = 1)
@TypeConverters(CurrencyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun accountDao(): AccountDao

    abstract fun rateDao(): RateDao

    abstract fun transactionDao(): TransactionDao

    abstract fun transactionAccountDao(): TransactionAccountDao

    abstract fun transactionAccountJoinDao(): TransactionAccountJoinDao
}