package com.storiqa.storiqawallet.di.modules

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.dao.AccountDao
import com.storiqa.storiqawallet.data.db.dao.RateDao
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.repository.*
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.network.CryptoCompareApi
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.utils.SignUtil
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    internal fun provideUserRepository(userDao: UserDao,
                                       walletApi: WalletApi,
                                       appDataStorage: IAppDataStorage,
                                       signUtil: SignUtil): IUserRepository {

        return UserRepository(userDao, walletApi, appDataStorage, signUtil)
    }

    @Provides
    @PerApplication
    internal fun provideAccountsRepository(userDao: UserDao,
                                           accountDao: AccountDao,
                                           walletApi: WalletApi,
                                           appDataStorage: IAppDataStorage,
                                           signUtil: SignUtil): IAccountsRepository {

        return AccountsRepository(userDao, accountDao, walletApi, appDataStorage, signUtil)
    }

    @Provides
    @PerApplication
    internal fun provideRatesRepository(rateDao: RateDao,
                                        cryptoCompareApi: CryptoCompareApi): IRatesRepository {

        return RatesRepository(rateDao, cryptoCompareApi)
    }

}