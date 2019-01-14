package com.storiqa.storiqawallet.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.dao.AccountDao
import com.storiqa.storiqawallet.data.db.dao.UserDao
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

}