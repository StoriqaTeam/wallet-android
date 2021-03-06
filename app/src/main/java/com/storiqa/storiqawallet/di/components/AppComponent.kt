package com.storiqa.storiqawallet.di.components

import android.content.Context
import android.content.res.Resources
import com.storiqa.cryptokeys.IKeyGenerator
import com.storiqa.cryptokeys.ISigner
import com.storiqa.storiqawallet.data.ITokenProvider
import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.dao.*
import com.storiqa.storiqawallet.data.network.CryptoCompareApi
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.polling.IShortPolling
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.di.modules.AppModule
import com.storiqa.storiqawallet.di.modules.NetworkModule
import com.storiqa.storiqawallet.di.modules.RepositoryModule
import com.storiqa.storiqawallet.di.modules.RoomModule
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.utils.PrefUtil
import dagger.Component

@PerApplication
@Component(modules = [AppModule::class, NetworkModule::class, RoomModule::class, RepositoryModule::class])
interface AppComponent : AppComponentProvides {

}

interface AppComponentProvides {

    @AppContext
    fun appContext(): Context

    fun resources(): Resources
    fun walletApi(): WalletApi
    fun cryptoCompareApi(): CryptoCompareApi

    fun prefUtil(): PrefUtil

    fun userDataStorage(): IUserDataStorage
    fun appDataStorage(): IAppDataStorage

    fun shortPolling(): IShortPolling

    fun signer(): ISigner
    fun keyGenerator(): IKeyGenerator
    fun tokenProvider(): ITokenProvider

    fun roomDatabase(): AppDatabase
    fun userDao(): UserDao
    fun accountDao(): AccountDao
    fun rateDao(): RateDao
    fun provideTransactionDao(): TransactionDao
    fun provideTransactionAccountDao(): TransactionAccountDao
    fun provideTransactionAccountJoinDao(): TransactionAccountJoinDao
    fun provideBlockchainIdDao(): BlockchainIdDao

    fun userRepository(): IUserRepository
    fun accountsRepository(): IAccountsRepository
    fun ratesRepository(): IRatesRepository
    fun transactionsRepository(): ITransactionsRepository
}