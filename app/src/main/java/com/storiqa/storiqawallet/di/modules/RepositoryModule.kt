package com.storiqa.storiqawallet.di.modules

import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.dao.*
import com.storiqa.storiqawallet.data.network.CryptoCompareApi
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.repository.*
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.utils.SignUtil
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    internal fun provideUserRepository(
            userDao: UserDao,
            walletApi: WalletApi,
            appDataStorage: IAppDataStorage,
            signUtil: SignUtil): IUserRepository {
        return UserRepository(userDao, walletApi, appDataStorage, signUtil)
    }

    @Provides
    @PerApplication
    internal fun provideAccountsRepository(
            userDao: UserDao,
            accountDao: AccountDao,
            rateDao: RateDao,
            walletApi: WalletApi,
            appDataStorage: IAppDataStorage,
            signUtil: SignUtil): IAccountsRepository {
        return AccountsRepository(userDao, accountDao, rateDao, walletApi, appDataStorage, signUtil)
    }

    @Provides
    @PerApplication
    internal fun provideRatesRepository(
            rateDao: RateDao,
            cryptoCompareApi: CryptoCompareApi): IRatesRepository {
        return RatesRepository(rateDao, cryptoCompareApi)
    }

    @Provides
    @PerApplication
    internal fun provideTransactionsRepository(
            walletApi: WalletApi,
            appDatabase: AppDatabase,
            transactionAccountJoinDao: TransactionAccountJoinDao,
            transactionAccountDao: TransactionAccountDao,
            transactionDao: TransactionDao,
            blockchainIdDao: BlockchainIdDao,
            appDataStorage: IAppDataStorage,
            signUtil: SignUtil): ITransactionsRepository {
        return TransactionsRepository(walletApi, appDatabase, transactionAccountJoinDao,
                transactionAccountDao, transactionDao, blockchainIdDao, appDataStorage, signUtil)
    }

}