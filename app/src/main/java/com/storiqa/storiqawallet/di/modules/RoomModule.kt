package com.storiqa.storiqawallet.di.modules

import android.app.Application
import androidx.room.Room
import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.dao.*
import com.storiqa.storiqawallet.di.scopes.PerApplication
import dagger.Module
import dagger.Provides


@Module
class RoomModule(application: Application) {

    private val databaseName = "wallet-database"

    private val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(application, AppDatabase::class.java, databaseName).build()
    }

    @Provides
    @PerApplication
    internal fun provideRoomDatabase(): AppDatabase = appDatabase

    @Provides
    @PerApplication
    internal fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @PerApplication
    internal fun provideAccountDao(appDatabase: AppDatabase): AccountDao = appDatabase.accountDao()

    @Provides
    @PerApplication
    internal fun provideRateDao(appDatabase: AppDatabase): RateDao = appDatabase.rateDao()

    @Provides
    @PerApplication
    internal fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao = appDatabase.transactionDao()

    @Provides
    @PerApplication
    internal fun provideTransactionAccountDao(appDatabase: AppDatabase): TransactionAccountDao = appDatabase.transactionAccountDao()

    @Provides
    @PerApplication
    internal fun provideTransactionAccountJoinDao(appDatabase: AppDatabase): TransactionAccountJoinDao = appDatabase.transactionAccountJoinDao()

    @Provides
    @PerApplication
    internal fun provideBlockchainIdDao(appDatabase: AppDatabase): BlockchainIdDao = appDatabase.blockchainIdDao()

}